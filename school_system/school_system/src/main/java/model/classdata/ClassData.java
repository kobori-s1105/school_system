package model.classdata;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * クラス（学級）を表すモデル
 * classes テーブルに対応
 */
public class ClassData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer classId;       // 主キー
    private String className;      // クラス名（例: 1A）
    private Integer courseId;      // 紐づくコースID
    private Integer schoolId;      // 紐づく学校ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- コンストラクタ ---
    public ClassData() {}

    public ClassData(Integer classId, String className, Integer courseId, Integer schoolId) {
        this.classId = classId;
        this.className = className;
        this.courseId = courseId;
        this.schoolId = schoolId;
    }

    // --- getter/setter ---
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // --- toString（デバッグ用）---
    @Override
    public String toString() {
        return "ClassData{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", courseId=" + courseId +
                ", schoolId=" + schoolId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

