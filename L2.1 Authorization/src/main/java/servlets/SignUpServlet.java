package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class SignUpServlet extends HttpServlet {
	private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }
	public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String pass = request.getParameter("password");
		
		if (login == null || pass == null) {
		 response.setContentType("text/html;charset=utf-8");
		 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		 return;
		}
		
		UserProfile profile = accountService.getUserByLogin(login);
		if (profile == null) {
		 response.setContentType("text/html;charset=utf-8");
		 response.getWriter().println("Unauthorized");
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 return;
		}
		
		accountService.addSession(request.getSession().getId(), profile);
		Gson gson = new Gson();
		String json = gson.toJson(profile);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println("Authorized: "+ login);
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
