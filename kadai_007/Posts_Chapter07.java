package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement statement = null;

		int count = 1;

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"パスワード");

			System.out.println("データベース接続成功:" + con);

			// SQLクエリを準備（追加）
			String sql = "INSERT INTO posts(user_id,posted_at, post_content, likes) VALUES (1003,'2023-02-08','昨日の夜は徹夜でした・・',13),(1002,'2023-02-08','お疲れ様です！',12),(1003,'2023-02-09','今日も頑張ります！',18),(1001,'2023-02-09','無理は禁物ですよ！',17),(1002,'2023-02-10','明日から連休ですね！',20);";
			statement = con.prepareStatement(sql);

			// SQLクエリを実行（追加）
			int rowCnt = statement.executeUpdate();

			System.out.println("レコード追加を実行します");
			System.out.println(rowCnt + "件のレコードが追加されました");

			//SQLクエリを準備（検索）
			String sql2 = "SELECT * FROM posts WHERE user_id = 1002 ;";
			ResultSet result = statement.executeQuery(sql2);

			//SQLクエリを実行（検索）
			System.out.println("ユーザーIDが" + 1002 + "のレコードを検索しました");

			while (result.next()) {

				Date postedAt = result.getDate("posted_at");
				String content = result.getString("post_content");
				String evaluate = result.getString("likes");
				System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "/いいね数=" + evaluate);
				count++;
			}
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
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

