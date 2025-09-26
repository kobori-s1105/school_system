package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.classdata.ClassData;

public class ClassListDao extends BaseDao {

    /** 一覧（検索・ページング） */
    public List<ClassData> findAll(Integer schoolId, Integer courseId, String keyword,
                                   int limit, int offset) throws SQLException {
        String sql = """
            SELECT class_id, class_name, course_id, school_id, created_at, updated_at
            FROM classes
            WHERE (? IS NULL OR school_id = ?)
              AND (? IS NULL OR course_id = ?)
              AND (? IS NULL OR class_name LIKE CONCAT('%', ?, '%'))
            ORDER BY class_name ASC, class_id ASC
            LIMIT ? OFFSET ?
        """;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

            if (courseId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, courseId); ps.setInt(i++, courseId); }

            if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
            else { ps.setString(i++, keyword); ps.setString(i++, keyword); }

            ps.setInt(i++, limit);
            ps.setInt(i, offset);

            List<ClassData> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        }
    }

    /** 件数（同条件） */
    public int countAll(Integer schoolId, Integer courseId, String keyword) throws SQLException {
        String sql = """
            SELECT COUNT(*)
            FROM classes
            WHERE (? IS NULL OR school_id = ?)
              AND (? IS NULL OR course_id = ?)
              AND (? IS NULL OR class_name LIKE CONCAT('%', ?, '%'))
        """;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

            if (courseId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, courseId); ps.setInt(i++, courseId); }

            if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
            else { ps.setString(i++, keyword); ps.setString(i++, keyword); }

            try (ResultSet rs = ps.executeQuery()) {
                rs.next(); return rs.getInt(1);
            }
        }
    }

    /** 主キー単体取得（編集前表示など） */
    public ClassData findById(int classId) throws SQLException {
        String sql = """
            SELECT class_id, class_name, course_id, school_id, created_at, updated_at
            FROM classes WHERE class_id = ?
        """;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    private ClassData map(ResultSet rs) throws SQLException {
        ClassData c = new ClassData();
        c.setClassId(rs.getInt("class_id"));
        c.setClassName(rs.getString("class_name"));
        c.setCourseId(rs.getInt("course_id"));
        c.setSchoolId(rs.getInt("school_id"));
        Timestamp cat = rs.getTimestamp("created_at");
        Timestamp uat = rs.getTimestamp("updated_at");
        c.setCreatedAt(cat != null ? cat.toLocalDateTime() : null);
        c.setUpdatedAt(uat != null ? uat.toLocalDateTime() : null);
        return c;
    }
}
