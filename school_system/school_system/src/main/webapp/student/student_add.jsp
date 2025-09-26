<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.classdata.ClassData" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>生徒追加</title>
<style>
  label { display:inline-block; width:140px; margin:4px 0; }
  input[type="text"], input[type="date"], input[type="number"], select { width:260px; }
  .row { margin-bottom:6px; }
</style>
</head>
<body>

<h2>生徒追加</h2>

<%
    // エラー表示（任意）
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
    <p style="color:red;"><%= errorMessage %></p>
<%
    }
%>

<form action="<%=request.getContextPath()%>/StudentAddServlet" method="post" accept-charset="UTF-8">
  <!-- students（基本情報） -->
  <div class="row">
    <label>学籍番号</label>
    <input type="text" name="student_number" required>
  </div>

  <div class="row">
    <label>氏名（姓）</label>
    <input type="text" name="last_name" required>
  </div>
  <div class="row">
    <label>氏名（名）</label>
    <input type="text" name="first_name" required>
  </div>

  <div class="row">
    <label>ふりがな（せい）</label>
    <input type="text" name="last_name_kana">
  </div>
  <div class="row">
    <label>ふりがな（めい）</label>
    <input type="text" name="first_name_kana">
  </div>

  <div class="row">
    <label>生年月日</label>
    <input type="date" name="birth_date">
  </div>

  <div class="row">
    <label>性別</label>
    <select name="gender_id">
      <option value="">（未選択）</option>
      <option value="1">男性</option>
      <option value="2">女性</option>
    </select>
  </div>

  <div class="row">
    <label>郵便番号</label>
    <input type="text" name="postal_code">
  </div>
  <div class="row">
    <label>都道府県</label>
    <input type="text" name="prefecture">
  </div>
  <div class="row">
    <label>市区町村</label>
    <input type="text" name="city">
  </div>
  <div class="row">
    <label>番地・建物名</label>
    <input type="text" name="address_line">
  </div>
  <div class="row">
    <label>電話番号</label>
    <input type="text" name="tel">
  </div>

  <!-- enrollments（現在在籍：学年/クラス/開始日） -->
  <hr>
  <div class="row">
    <label>学年</label>
    <input type="number" name="grade" min="1" max="3" required>
  </div>

  <div class="row">
    <label>クラス</label>
    <%
      @SuppressWarnings("unchecked")
      List<ClassData> classList = (List<ClassData>) request.getAttribute("classList");
      Integer selectedClassId = (Integer) request.getAttribute("selectedClassId"); // 任意
      out.println("<!-- classList=" + (classList==null?"null":classList.size()) + " -->");
    %>
    <select name="class_id" required>
      <%
        if (classList != null && !classList.isEmpty()) {
          for (ClassData c : classList) {
            boolean sel = (selectedClassId != null && selectedClassId == c.getClassId());
      %>
        <option value="<%= c.getClassId() %>" <%= sel ? "selected" : "" %>><%= c.getClassName() %></option>
      <%
          }
        } else {
      %>
        <option value="">クラスがありません</option>
      <%
        }
      %>
    </select>
    <a href="<%=request.getContextPath()%>/ClassAddServlet" target="_blank">クラスを追加</a>
  </div>

  <div class="row">
    <label>在籍開始日</label>
    <input type="date" name="start_date">
    <small>※未指定ならサーバ側で当日を設定</small>
  </div>

  <hr>
  <div class="row">
    <input type="submit" value="追加">
    <a href="<%=request.getContextPath()%>/StudentListServlet" style="margin-left:12px;">一覧に戻る</a>
  </div>
</form>

</body>
</html>
