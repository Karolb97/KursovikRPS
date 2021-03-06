package DAOImpl;

import Models.People;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Models.Candidate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Models.InformationCandidate;

public class CandidateDAO {
    
    ArrayList<Candidate> candidates;
    private static Connection con;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    
    public CandidateDAO(Connection conn) {
        this.con = conn;
    }
    
    public List<Candidate> getAllCandidate() throws SQLException, IOException {
        candidates = new ArrayList<>();
        String query = "select * from Candidate, InformationCandidate, People where Candidate.NumberPassportC = People.NumberPassport and Candidate.idInformationVoter = InformationCandidate.idInformationCandidate";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            Candidate c = new Candidate();
            People p = new People();
            InformationCandidate ic = new InformationCandidate();
            c.setNumberPassportC(rs.getLong(1));
            c.setIdInformationVoter(rs.getInt(2));
            ic.setIdInformationCandidate(rs.getInt(3));
            ic.setNumberVoter(rs.getInt(4));
            ic.setPlaceInList(rs.getInt(5));
            ic.setDescription(rs.getString(6));
            ic.setNameImage(rs.getString(7));
            ic.setNameVideo(rs.getString(8));
            p.setNumberPassport(rs.getLong(9));
            p.setDateOfBirthday(rs.getString(10));
            p.setFirstName(rs.getString(11));
            p.setSecondName(rs.getString(12));
            p.setMiddleName(rs.getString(13));
            c.setPeople(p);
            c.setInformationcandidate(ic);
            candidates.add(c);
        }
        rs.close();
        return candidates;
    }
    
    public List<Candidate> getSortCandidate() throws SQLException, IOException {
        candidates = new ArrayList<>();
        String query = "select * from Candidate, InformationCandidate, People where Candidate.NumberPassportC = People.NumberPassport and Candidate.idInformationVoter = InformationCandidate.idInformationCandidate order by PlaceInList asc;";
        pstmt = con.prepareStatement(query);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            Candidate c = new Candidate();
            People p = new People();
            InformationCandidate ic = new InformationCandidate();
            p.setFirstName(rs.getString(11));
            p.setSecondName(rs.getString(12));
            p.setMiddleName(rs.getString(13));
            ic.setIdInformationCandidate(rs.getInt(3));
            ic.setNumberVoter(rs.getInt(4));
            ic.setPlaceInList(rs.getInt(5));
            ic.setDescription(rs.getString(6));
            ic.setNameImage(rs.getString(7));
            ic.setNameVideo(rs.getString(8));
            c.setNumberPassportC(rs.getLong(1));
            c.setIdInformationVoter(rs.getInt(2));
            c.setPeople(p);
            c.setInformationcandidate(ic);
            candidates.add(c);
        }
        rs.close();
        return candidates;
    }
    
    public Candidate getCandidate(People people) throws SQLException{
        pstmt = con.prepareStatement("select * from Candidate where NumberPassportC = ?");
        pstmt.setLong(1, people.getNumberPassport());
        rs = pstmt.executeQuery();
        Candidate candidate = new Candidate();
        while (rs.next()) {
            candidate.setNumberPassportC(rs.getLong(1));
            candidate.setIdInformationVoter(rs.getInt(2));
        }
        return candidate;
    }
}
