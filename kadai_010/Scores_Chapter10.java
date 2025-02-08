package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Scores_Chapter10 {

	public static void main(String[] args) {

		Scanner scanner = null;
		Connection con = null;
		Statement statement = null;

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"パスワード");
			System.out.println("データベース接続成功：" + con);

			statement = con.createStatement();
			String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id =5;";
			System.out.println("レコード更新を実行します");

			int rowCnt = statement.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが実行されました");

			System.out.println("数学・英語の点数が高い順に並べ替えました");

			String sql2 = "SELECT * FROM scores ORDER BY score_math DESC,score_english DESC";
			ResultSet result = statement.executeQuery(sql2);

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int scoreMath = result.getInt("score_math");
				int scoreEnglish = result.getInt("score_english");
				System.out.println(
						result.getRow() + "件目：生徒ID=" + id + "/氏名=" + name + "/数学=" + scoreMath + "/英語=" + scoreEnglish);
			}
		}

		catch (InputMismatchException e) {
			System.out.println("入力が正しくありません");
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}

	}
}
