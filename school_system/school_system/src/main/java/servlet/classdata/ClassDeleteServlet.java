package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassDeleteDao;

@WebServlet("/classes/delete")
public class ClassDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/classes"); return; }

        try {
            int id = Integer.parseInt(idStr);
            boolean ok = new ClassDeleteDao().delete(id);
            String qs = ok ? "deleted=1" : "deleted=0";
            resp.sendRedirect(req.getContextPath()+"/classes?"+qs);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
        } catch (SQLException e) {
            // 外部キー制約違反など
            resp.sendRedirect(req.getContextPath()+"/classes?error=constraint");
        }
    }
}
