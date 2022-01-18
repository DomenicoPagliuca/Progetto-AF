package it.unisa.agency_formation.autenticazione.manager;

import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AutenticazioneManager {
    boolean registrazione(Utente user) throws SQLException;
    Utente login(String email, String password) throws SQLException;
    //Utente getDatiUtente(int idUser) throws SQLException;
    Dipendente getDipendente(int idUser) throws SQLException; //aggiunto questo
    boolean assumiCandidato(Dipendente dipendente) throws SQLException;
   // ArrayList<Utente> getCandidati() throws SQLException;
    ArrayList<Utente> getCandidatiConCandidatura() throws SQLException;
    //ArrayList<Utente> getUtentiByRuolo(RuoliUtenti ruolo) throws SQLException;
    ArrayList<Dipendente> getTuttiDipendenti() throws SQLException;
    ArrayList<Dipendente> getDipendentiByStato(StatiDipendenti state) throws SQLException;
    boolean modificaRuolo(int idUtente) throws SQLException;
    ArrayList<Utente> getCandidatiColloquio() throws SQLException;
    boolean setTeamDipendente(int dip, int idTeam) throws SQLException;
}
