package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.base.BaseDao;
import model.classdata.ClassData;

public class ClassAddDao extends BaseDao {

    /** 追加：採番IDを返す（0は失敗） */
    public int insert(ClassData c) throws SQLException {
        String sql = "INSERT INTO classes (class_name, course_id, school_id) VALUES (?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getCourseId());
            ps.setInt(3, c.getSchoolId());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
            return 0;
        }
    }
}
