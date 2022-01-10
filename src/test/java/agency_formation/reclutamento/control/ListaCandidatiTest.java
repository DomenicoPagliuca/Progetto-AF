package agency_formation.reclutamento.control;

import it.unisa.agency_formation.autenticazione.manager.AutenticazioneManagerImpl;
import it.unisa.agency_formation.reclutamento.control.ListaCandidati;
import org.junit.jupiter.api.Test;
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
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

public class ListaCandidatiTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private ServletConfig config;
    private ServletContext context;

    @Test
    public void viewCandidatiPass() throws ServletException, IOException {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        config = Mockito.mock(ServletConfig.class);
        context = Mockito.mock(ServletContext.class);
        ListaCandidati servlet = new ListaCandidati();
        Mockito.when(request.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);
        servlet.init(config);
        servlet.doGet(request,response);
        assertTrue(stringWriter.toString().equals("1"));
    }
    @Test
    public void viewCandidatiFail() throws ServletException, IOException, SQLException {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        config = Mockito.mock(ServletConfig.class);
        context = Mockito.mock(ServletContext.class);
        AutenticazioneManagerImpl aut = Mockito.mock(AutenticazioneManagerImpl.class);
        Mockito.when(aut.getCandidati()).thenReturn(null);
        ListaCandidati servlet = new ListaCandidati();
        Mockito.when(request.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        //Mockito.when(request.getAttribute("aut")).thenReturn(aut);







        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);
        servlet.init(config);
        servlet.doGet(request,response);
        assertTrue(stringWriter.toString().equals("2"));
    }
}