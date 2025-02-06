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
		int totalInsertedRecords = 0;
		int count = 1;

		String[][] list = {
				{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"パスワード");

			System.out.println("データベース接続成功:" + con);

			// SQLクエリを準備
			String sql = "INSERT INTO posts(user_id,posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			statement = con.prepareStatement(sql);

			// リストの1行目から順番に読み込む
			for (int i = 0; i < list.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換え
				statement.setString(1, list[i][0]);
				statement.setString(2, list[i][1]);
				statement.setString(3, list[i][2]);
				statement.setString(4, list[i][3]);

				// SQLクエリを実行（DBMSに送信）
				int rowCnt = statement.executeUpdate();
				totalInsertedRecords += rowCnt; // 合計件数を加算
			}
			System.out.println("レコード追加を実行します");
			System.out.println(totalInsertedRecords + "件のレコードが追加されました");

			//SQLクエリを準備
			String sql2 = "SELECT user_id, posted_at, post_content, likes FROM posts;";
			ResultSet result = statement.executeQuery(sql2);

			while (result.next()) {

				String id = result.getString("user_id");
				Date postedAt = result.getDate("posted_at");
				String content = result.getString("post_content");
				String evaluate = result.getString("likes");
				if (id.equals("1002")) {
					System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "/いいね数=" + evaluate);
					count++;
				}
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
