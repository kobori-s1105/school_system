package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassAddDao;
import model.classdata.ClassData;

@WebServlet("/classes/add")
public class ClassAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 空のフォーム表示
        req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String className = req.getParameter("class_name");
        String courseIdStr = req.getParameter("course_id");
        String schoolIdStr = req.getParameter("school_id");

        // 簡易バリデーション
        if (className == null || className.isBlank() ||
            courseIdStr == null || schoolIdStr == null) {
            req.setAttribute("errorMessage", "必須項目（クラス名／コースID／学校ID）が未入力です。");
            req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            int schoolId = Integer.parseInt(schoolIdStr);

            ClassData c = new ClassData();
            c.setClassName(className.trim());
            c.setCourseId(courseId);
            c.setSchoolId(schoolId);

            int newId = new ClassAddDao().insert(c);
            if (newId == 0) {
                req.setAttribute("errorMessage", "登録に失敗しました。");
                req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/classes?created=" + newId);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "IDは数値で入力してください。");
            req.getRequestDispatcher("/WEB-INF/views/classdata/form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
