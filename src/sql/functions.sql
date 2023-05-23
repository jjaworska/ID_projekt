create or replace function autor_role (autor int, utwory_autorzy int) returns text as
$$
declare
	s text;
	i record;
	c int :=0 ;
begin
	s := (SELECT a.nazwa from autorzy a where id_autora=autor);
	FOR i IN SELECT r.nazwa from role r join utwory_autorzy_role using(id_roli)
	where id_utwory_autorzy = utwory_autorzy LOOP
	if c=0 then s := concat(s, ' - '); c := 1; else s:= concat(s, ', '); end if;
	s := concat(s, i.nazwa);
	END LOOP;
	return s;
END; $$ language plpgsql;

create or replace function autorzy (index int) returns text[] as
$$
begin 
    RETURN ARRAY(
        SELECT autor_role(id_autora, id_utwory_autorzy) from utwory_autorzy
	where id_utworu = index
    );
END; $$ language plpgsql;

drop view if exists utwory_widok;
create or replace view utwory_widok as
select u.id_utworu, u.tytul, autorzy(u.id_utworu) as autorzy, u.data_wydania, u.dlugosc, g.nazwa as gatunek,
case when u.oceny_liczba >0 then round(u.oceny_suma::numeric/u.oceny_liczba, 2) else null end as ocena,
a.nazwa as album from utwory u join gatunki g using(id_gatunku) left outer join albumy a using(id_albumu)
order by ocena desc nulls last;

drop view if exists uzytkownicy_widok;
create or replace view uzytkownicy_widok as
select u.nazwa,
(select count(*) from obserwujacy where id_obserwowanego = u.id_uzytkownika) as obserwujacy,
(select count(*) from obserwujacy where id_obserwujacego = u.id_uzytkownika) as obserwuje
from uzytkownicy u order by 2 desc;

create or replace function ocena(index int, d date) returns numeric as
$$
declare
	r record;
	sum numeric :=0;
	counter int :=0;
begin 
	FOR r IN SELECT id_uzytkownika, MAX(data) as data FROM oceny
		where id_utworu = index and data<=d GROUP BY id_uzytkownika
	LOOP
	counter := counter+1;
	sum := sum+ (SELECT ocena from oceny
	where id_utworu = index and id_uzytkownika = r.id_uzytkownika and data=r.data);
	END LOOP;
if counter=0 then return null; end if;
return round(sum/counter, 2);
END; $$ language plpgsql;

create or replace function album_autorzy(index int) returns text[] as
$$
declare
	c integer;
begin 
	c := count(*) from utwory where id_albumu = index;
	return array(
		select a.nazwa from autorzy a
		join utwory_autorzy au using(id_autora)
		join utwory u using (id_utworu)
		where u.id_albumu=index group by a.id_autora
	);
END; $$ language plpgsql;

create or replace function rekomendacje (uzytkownik int) returns int[] as
$$
declare
	watched int[];
	best int[];
	r record;
	rec int[];
begin 
	watched := array(select id_utworu from oceny where id_uzytkownika = uzytkownik);
	best := array(select id_autora from utwory_autorzy join oceny using(id_utworu)
		where id_uzytkownika = uzytkownik and ocena>=8);
	for r in select * from utwory_autorzy where id_autora = ANY(best) loop
		if not r.id_utworu = any(watched) then
			watched := array_append(watched, r.id_utworu);
			rec := array_append(rec, r.id_utworu);
		end if;
		if array_length(rec, 1)=10 then
		return rec;
		end if;
	end loop;
	best := array(select id_gatunku from utwory join oceny using(id_utworu)
		where id_uzytkownika = uzytkownik and ocena>=8);
	for r in select * from utwory where id_gatunku = ANY(best) order by oceny_suma desc loop
		if not r.id_utworu = any(watched) then
			watched := array_append(watched, r.id_utworu);
			rec := array_append(rec, r.id_utworu);
		end if;
		if array_length(rec, 1)=10 then
		return rec;
		end if;
	end loop;
	for r in select * from utwory order by oceny_suma desc loop
		if not r.id_utworu = any(watched) then
			watched := array_append(watched, r.id_utworu);
			rec := array_append(rec, r.id_utworu);
		end if;
		if array_length(rec, 1)=10 then
		return rec;
		end if;
	end loop;
	return rec;
END; $$ language plpgsql;

create or replace function widoczne_playlisty(uzytkownik int)
returns table (id int, nazwa varchar(100), id_tworcy int, nazwa_tworcy varchar(20)) as
$$ BEGIN
return query
	select p.id_playlisty, p.nazwa, p.tworca, u.nazwa from playlisty p
	join uzytkownicy u on p.tworca = u.id_uzytkownika
	left outer join obserwujacy o on p.tworca = o.id_obserwowanego
	where p.dostep = 'P' or (p.dostep = 'O' and o.id_obserwujacego=uzytkownik)
	or (p.dostep = 'O' and p.tworca=uzytkownik)
	or (p.dostep = 'U' and p.tworca=uzytkownik);
end; $$ language plpgsql;

CREATE OR REPLACE FUNCTION wypisz_role_autora (id integer)
RETURNS text[]
AS $$
	BEGIN
		RETURN ARRAY(SELECT nazwa
			FROM utwory_autorzy
			NATURAL JOIN utwory_autorzy_role
			NATURAL JOIN role
			WHERE id_autora = id
		);
	END;
$$
LANGUAGE plpgsql;

