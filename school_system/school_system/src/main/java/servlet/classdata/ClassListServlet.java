package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassListDao;
import model.classdata.ClassData;

@WebServlet("/classes")
public class ClassListServlet extends HttpServlet {

    private int i(String s, int def){ try { return Integer.parseInt(s); } catch(Exception e){ return def; } }
    private Integer I(String s){ try { return (s==null||s.isBlank())?null:Integer.valueOf(s); } catch(Exception e){ return null; } }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        Integer schoolId = I(req.getParameter("school_id"));
        Integer courseId = I(req.getParameter("course_id"));

        int page = Math.max(1, i(req.getParameter("page"), 1));
        int limit = 20;
        int offset = (page - 1) * limit;

        ClassListDao dao = new ClassListDao();
        try {
            List<ClassData> list = dao.findAll(schoolId, courseId, q, limit, offset);
            int total = dao.countAll(schoolId, courseId, q);
            int pages = (int)Math.ceil(total / (double)limit);

            req.setAttribute("list", list);
            req.setAttribute("q", q);
            req.setAttribute("school_id", schoolId);
            req.setAttribute("course_id", courseId);
            req.setAttribute("page", page);
            req.setAttribute("pages", pages);

            req.getRequestDispatcher("/WEB-INF/views/classdata/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
