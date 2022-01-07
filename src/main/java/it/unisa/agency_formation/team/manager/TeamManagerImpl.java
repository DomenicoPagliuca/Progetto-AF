package it.unisa.agency_formation.team.manager;

import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAO;
import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.team.DAO.TeamDAO;
import it.unisa.agency_formation.team.domain.Team;

import javax.lang.model.type.ArrayType;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamManagerImpl implements TeamManager {

    @Override
    public boolean createTeam(Team team, int idUtente) throws SQLException {
       return TeamDAO.doSaveTeam(team, idUtente);
    }

    @Override
    public ArrayList<Dipendente> getEmployee(StatiDipendenti state) throws SQLException {
        return DipendenteDAO.doRetrieveByState(state);
    }

    @Override
    public boolean addEmployee(int idTeam, Dipendente dip) throws SQLException {
       return TeamDAO.addEmployee(idTeam, dip.getIdDipendente());
    }

    @Override
    public boolean removeEmployee(int idDip) throws SQLException {
        return TeamDAO.removeEmployee(idDip);
    }

    @Override
    public ArrayList<Team> viewTeams(int idUtente) throws SQLException {
        return TeamDAO.doRetrieveTMTeam(idUtente);
    }

    @Override
    public ArrayList<Team> viewAllTeams() throws SQLException {
        return TeamDAO.doRetrieveAllTeam();
    }

    @Override
    public boolean disbandTeam(int idTeam) throws SQLException {
        return TeamDAO.doRemoveTeam(idTeam);
    }

    @Override
    public int viewLastIdTeam() throws SQLException {
        return TeamDAO.doRetrieveLastIDTeam();
    }

    @Override
    public boolean updateDipOnTeam(int idDip, int idTeam) throws SQLException {
       return DipendenteDAO.updateDipTeamAndState(idDip, idTeam);
    }

    @Override
    public ArrayList<Integer> retriveDips(int idTeam) throws SQLException {
        return TeamDAO.doRetrieveIdEmployees(idTeam);
    }

    @Override
    public boolean updateDipsDisso(int idDip) throws SQLException {
       return TeamDAO.updateDipStateDissolution(idDip);

    }

    @Override
    public boolean deleteTeam(int idTeam) throws SQLException {
        return TeamDAO.doRemoveTeam(idTeam);

    }

    @Override
    public ArrayList<Dipendente> retrieveAllDipsTeam() throws SQLException {
        return TeamDAO.doRetrieveAllDipsTeam();

    }

}
