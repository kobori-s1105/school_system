package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class ClassDeleteDao extends BaseDao {

    /**
     * 削除：1件削除なら true
     * 注意：外部キー参照（enrollments, subject_offerings など）があると RESTRICT で例外になります。
     */
    public boolean delete(int classId) throws SQLException {
        String sql = "DELETE FROM classes WHERE class_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, classId);
            return ps.executeUpdate() == 1;
        }
    }
}
