package agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.domain.RuoliUtenti;
import it.unisa.agency_formation.autenticazione.domain.Utente;
import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.reclutamento.control.ListaCandidati;
import it.unisa.agency_formation.reclutamento.control.ViewCandidaturaControl;
import it.unisa.agency_formation.reclutamento.domain.Candidatura;
import it.unisa.agency_formation.reclutamento.domain.StatiCandidatura;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

public class ListaCandidatiTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private ServletConfig config;
    private ServletContext context;

    @Test //User non HR
    public void candidatiNull() throws ServletException, IOException {
        Utente user = new Utente();
        user.setId(1);
        user.setRole(RuoliUtenti.CANDIDATO);
        user.setPwd("lol1");
        user.setEmail("l.giacchetti@studenti.unisa.it");
        user.setName("Luigi");
        user.setSurname("Giacchetti");
        ArrayList<Utente> List = null;
        config = Mockito.mock(ServletConfig.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        context = Mockito.mock(ServletContext.class);
        ListaCandidati servlet = Mockito.spy(ListaCandidati.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        Mockito.when(request.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        try (MockedStatic mockedStatic = mockStatic(ListaCandidati.class)) {
            mockedStatic.when(() -> ListaCandidati.getCandidatesFromManager()).thenReturn(List);
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(writer);
            servlet.init(config);
            servlet.doGet(request, response);
            assertEquals("2", stringWriter.toString());
        }
    }
    @Test
    public void candidatiPass() throws ServletException, IOException, SQLException {
        Utente user = new Utente();
        user.setId(1);
        user.setRole(RuoliUtenti.HR);
        user.setPwd("lol1");
        user.setEmail("l.giacchetti@studenti.unisa.it");
        user.setName("Luigi");
        user.setSurname("Giacchetti");
        ArrayList<Utente> List = null;
        config = Mockito.mock(ServletConfig.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        context = Mockito.mock(ServletContext.class);
        ListaCandidati servlet = Mockito.spy(ListaCandidati.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        Mockito.when(request.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        try (MockedStatic mockedStatic = mockStatic(ListaCandidati.class)) {
            mockedStatic.when(() -> ListaCandidati.getCandidatesFromManager()).thenReturn(List);
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(writer);
            servlet.init(config);
            servlet.doGet(request, response);
            assertEquals("1", stringWriter.toString());
        }
    }
}
