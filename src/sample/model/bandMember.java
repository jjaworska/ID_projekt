package sample.model;

import java.sql.Date;
import java.sql.ResultSet;

public class bandMember extends Autor {
    private Date data_dolaczenia;
    private Date data_opuszczenia;
    public bandMember(ResultSet rs) {
        try {
            this.nazwa = rs.getString(2);
            this.czy_zespol = rs.getString(3);
            this.id_autora = rs.getInt(1);
            this.data_dolaczenia = rs.getDate(6);
            this.data_opuszczenia = rs.getDate(7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        if(data_opuszczenia != null)
            return nazwa + " (" + data_dolaczenia.toString() + " - " + data_opuszczenia.toString() + ")";
        return nazwa + " (od " + data_dolaczenia.toString() + ")";
    }
}
