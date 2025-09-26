package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.classdata.ClassData;

public class ClassUpdateDao extends BaseDao {

    /** 更新：1件更新なら true */
    public boolean update(ClassData c) throws SQLException {
        String sql = "UPDATE classes SET class_name=?, course_id=?, school_id=? WHERE class_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getCourseId());
            ps.setInt(3, c.getSchoolId());
            ps.setInt(4, c.getClassId());

            return ps.executeUpdate() == 1;
        }
    }
}
