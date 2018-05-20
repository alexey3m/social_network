import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountViewServlet extends HttpServlet {
    private static final String START_TAG_TD = "<td>";
    private static final String END_TAG_TD = "</td>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder("<html><head>\n" +
                "<style>\n" +
                "#accounts {\n" +
                "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "#accounts td, #accounts th {\n" +
                "    border: 1px solid #ddd;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                "#accounts tr:nth-child(even){background-color: #f2f2f2;}\n" +
                "\n" +
                "#accounts tr:hover {background-color: #ddd;}\n" +
                "\n" +
                "#accounts th {\n" +
                "    padding-top: 12px;\n" +
                "    padding-bottom: 12px;\n" +
                "    text-align: left;\n" +
                "    background-color: #4CAF50;\n" +
                "    color: white;\n" +
                "}\n" +
                "</style>\n" +
                "</head><body>");
        AccountService service = new AccountService();
        List<Account> accounts = service.getAll();
        sb.append("<table id=\"accounts\"><tr><th>Id</th><th>First name</th><th>Last name</th><th>Phone personally</th><th>Phone work</th><th>Address personally</th></tr>");
        for (Account account : accounts) {
            sb.append("<tr>");
            sb.append(START_TAG_TD).append(account.getId()).append(END_TAG_TD);
            sb.append(START_TAG_TD).append(account.getFirstName()).append(END_TAG_TD);
            sb.append(START_TAG_TD).append(account.getLastName()).append(END_TAG_TD);
            sb.append(START_TAG_TD).append(account.getPhonePers()).append(END_TAG_TD);
            sb.append(START_TAG_TD).append(account.getPhoneWork()).append(END_TAG_TD);
            sb.append(START_TAG_TD).append(account.getAddressPers()).append(END_TAG_TD);
            sb.append("</tr>");
        }
        sb.append("</table></html></body>");
        resp.getOutputStream().write(sb.toString().getBytes());
        req.getSession();
    }
}