package agency_formation.reclutamento.manager;

import it.unisa.agency_formation.autenticazione.DAO.DipendenteDAO;
import it.unisa.agency_formation.autenticazione.domain.Dipendente;
import it.unisa.agency_formation.autenticazione.domain.StatiDipendenti;
import it.unisa.agency_formation.reclutamento.DAO.CandidaturaDAO;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManager;
import it.unisa.agency_formation.reclutamento.manager.ReclutamentoManagerImpl;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static it.unisa.agency_formation.reclutamento.domain.StatiCandidatura.NonRevisionato;
import static it.unisa.agency_formation.reclutamento.domain.StatiCandidatura.Rifiutata;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class ReclutamentoManagerTest {
private ReclutamentoManager reclutamento = new ReclutamentoManagerImpl();

    @Test //upload pass
    public void caricaCandidatura1() throws SQLException {
        Candidatura cand = new Candidatura();
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        cand.setDocumentiAggiuntivi("test");
        cand.setStato(NonRevisionato);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.salvaCandidaturaSenzaDocumenti(cand)).thenReturn(true);
        }
        assertTrue(reclutamento.caricaCandidatura(cand));
    }

    @Test //candidatura = null not pass
    public void caricaCandidatura2() throws SQLException {
        Candidatura cand = null;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.salvaCandidaturaSenzaDocumenti(cand)).thenReturn(false);
        }
        assertFalse(reclutamento.caricaCandidatura(cand));
    }

    @Test //candidatura  alreadyLoaded non caricata
    public void caricaCandidatura3() throws SQLException {
        Candidatura cand = new Candidatura();
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        cand.setStato(NonRevisionato);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.doRetrieveCandidaturaById(cand.getIdCandidato())).thenReturn(null);
        }
        assertTrue(reclutamento.caricaCandidatura(cand));
    }

    @Test //candidatura  alreadyLoaded
    public void caricaCandidatura4() throws SQLException {
        Candidatura cand = new Candidatura();
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        cand.setDocumentiAggiuntivi("test");
        cand.setStato(NonRevisionato);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.doRetrieveCandidaturaById(cand.getIdCandidato())).thenReturn(cand);
        }
        assertTrue(reclutamento.caricaCandidatura(cand));
    }

    @Test //candidatura con curriculum null
    public void caricaCandidatura5() throws SQLException {
        Candidatura cand = new Candidatura();
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        cand.setIdCandidato(1);
        cand.setDocumentiAggiuntivi("test");
        cand.setStato(NonRevisionato);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.doRetrieveCandidaturaById(cand.getIdCandidato())).thenReturn(cand);
        }
        assertTrue(reclutamento.caricaCandidatura(cand));
    }

    @Test
    public void modificaStatoCandidaturaFail1() throws SQLException {
        Candidatura cand = new Candidatura();
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        cand.setStato(NonRevisionato);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.modificaStatoCandidatura(cand.getIdCandidato(), cand.getStato())).thenReturn(true);
        }
        assertFalse(reclutamento.caricaCandidatura(cand));
    }

    @Test //id<1
    public void getCandidaturaByIdFail1() throws SQLException{
        int id = 0;
        Candidatura cand = null;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.doRetrieveCandidaturaById(id)).thenReturn(cand);
        }
        assertNull(reclutamento.getCandidaturaById(id));
    }
    @Test //id di un candidato che non esiste
    public void getCandidaturaByIdFail2() throws SQLException{
        int id = 150;
        Candidatura cand = null;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.doRetrieveCandidaturaById(id)).thenReturn(cand);
        }
        assertNull(reclutamento.getCandidaturaById(id));
    }

    @Test //non ci devono essere candidature
    public void getTutteCandidature1() throws SQLException{
        ArrayList<Candidatura> candidature =null;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.recuperaCandidature()).thenReturn(candidature);
        }
        assertNotNull(reclutamento.getTutteCandidature());
    }

    @Test
    public void ricandidatura() throws SQLException {
        Candidatura candidatura = new Candidatura();
        candidatura.setStato(StatiCandidatura.Rifiutata);
        candidatura.setIdCandidatura(1);
        candidatura.setIdCandidato(1);
        candidatura.setCurriculum("test");
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        candidatura.setDataCandidatura(data);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.rimuoviCandidatura(candidatura.getIdCandidato())).thenReturn(true);
            assertTrue(reclutamento.ricandidatura(candidatura.getIdCandidato()));
        }
    }

    @Test
    public void getCandidatiConColloquio() throws SQLException {
        ArrayList<Candidatura> candidature = null;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.recuperaCandidatureByStato(NonRevisionato)).thenReturn(candidature);
            assertNull(reclutamento.getCandidatiConColloquio(NonRevisionato));
        }
    }

    @Test
    public void modificaStatoCandidatura() throws SQLException{
        Candidatura cand = new Candidatura();
        cand.setStato(StatiCandidatura.NonRevisionato);
        cand.setIdCandidatura(1);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.modificaStatoCandidatura(cand.getIdCandidatura(), cand.getStato())).thenReturn(true);
            assertTrue(reclutamento.modificaStatoCandidatura(cand.getIdCandidatura(), cand.getStato()));
        }
    }

    @Test //not pass , mi aspetto false
    public void accettaCandidatura() {
        int idCandidatura = 1;
        int idHR = 4;
        String data = ("2022-04-03");
        String tempo = ("17:30");
        String dataOra = data + " " + tempo;
        Date data1 = null;
        try {
            data1 = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dataOra);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(data1.getTime());
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.accettaCandidatura(idCandidatura, idHR, timestamp )).thenReturn(true);
        }
        try {
            assertTrue(reclutamento.accettaCandidatura(idCandidatura, idHR, timestamp));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test //not pass , mi aspetto false
    public void rifiutaCandidatura() {
        int idCandidatura = 1;
        int idHR = 4;
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.rifiutaCandidatura(idCandidatura, idHR )).thenReturn(true);
        }
        try {
            assertTrue(reclutamento.rifiutaCandidatura(idCandidatura, idHR));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    @Test
    public void assumiCandidato() throws SQLException {
        Dipendente dipendente = new Dipendente();
            dipendente.setAnnoNascita(2000);
            dipendente.setIdDipendente(1);
            dipendente.setResidenza("test");
            dipendente.setTelefono("118");
            dipendente.setStato(StatiDipendenti.DISPONIBILE);
            try (MockedStatic mocked = mockStatic(DipendenteDAO.class)) {
                mocked.when(() -> DipendenteDAO.salvaDipendente(dipendente)).thenReturn(true);
                assertTrue(reclutamento.assumiCandidato(dipendente));
            }
    }*/

    @Test
    public void rifiutaCandidato() throws SQLException {
        Candidatura cand = new Candidatura();
        cand.setStato(StatiCandidatura.NonRevisionato);
        cand.setIdCandidatura(1);
        cand.setIdCandidato(1);
        cand.setCurriculum("test");
        java.util.Date date = new java.util.Date();
        java.sql.Date data = new java.sql.Date(date.getTime());
        cand.setDataCandidatura(data);
        try (MockedStatic mocked = mockStatic(CandidaturaDAO.class)) {
            mocked.when(() -> CandidaturaDAO.modificaStatoCandidatura(cand.getIdCandidatura(), Rifiutata)).thenReturn(true);
            assertTrue(reclutamento.rifiutaCandidato(cand.getIdCandidatura()));
        }
    }
}
