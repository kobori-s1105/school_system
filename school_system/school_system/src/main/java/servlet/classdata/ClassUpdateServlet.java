package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassListDao;
import dao.classdata.ClassUpdateDao;
import model.classdata.ClassData;

@WebServlet("/classes/edit")
public class ClassUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/classes"); return; }
        try {
            int id = Integer.parseInt(idStr);
            ClassData c = new ClassListDao().findById(id);
            if (c == null) {
                resp.sendRedirect(req.getContextPath()+"/classes?notfound=1");
                return;
            }
            req.setAttribute("classData", c);
            req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("class_id");
        String className = req.getParameter("class_name");
        String courseIdStr = req.getParameter("course_id");
        String schoolIdStr = req.getParameter("school_id");

        if (idStr == null || className == null || className.isBlank() ||
            courseIdStr == null || schoolIdStr == null) {
            req.setAttribute("errorMessage", "必須項目（クラスID／クラス名／コースID／学校ID）が未入力です。");
            // 入力値を再セット
            if (idStr != null) {
                ClassData c = new ClassData();
                try { c.setClassId(Integer.parseInt(idStr)); } catch(Exception ignore){}
                c.setClassName(className);
                try { c.setCourseId(Integer.parseInt(courseIdStr)); } catch(Exception ignore){}
                try { c.setSchoolId(Integer.parseInt(schoolIdStr)); } catch(Exception ignore){}
                req.setAttribute("classData", c);
            }
            req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
            return;
        }

        try {
            ClassData c = new ClassData();
            c.setClassId(Integer.parseInt(idStr));
            c.setClassName(className.trim());
            c.setCourseId(Integer.parseInt(courseIdStr));
            c.setSchoolId(Integer.parseInt(schoolIdStr));

            boolean ok = new ClassUpdateDao().update(c);
            if (!ok) {
                req.setAttribute("errorMessage", "更新対象が見つかりませんでした。");
                req.setAttribute("classData", c);
                req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/classes?updated=" + c.getClassId());
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "IDは数値で入力してください。");
            req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
