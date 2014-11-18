package blog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Blogを操作するビジネスロジック
 * @author owner
 *
 */
public class BlogController {

	/**
	 * シングルトンのインスタンス
	 */
	private static BlogController controller = new BlogController();

	/**
	 * 連番用のカウンタ
	 */
	private int idCounter;

	/**
	 * Blogのリスト
	 */
	private List<Topic> topics = new ArrayList<Topic>();

	/**
	 * このクラスのインスタンスを取得
	 */
	public static BlogController getInstance(){
		return controller;
	}

	private BlogController(){

	}

	/**
	 * トピックを登録します
	 */
	public void postTopic(Topic topic){
		String sql = "insert into topic(title,content)"
				+ " values(" + "'" + topic.getTitle() + "'"
				+ ",'" + topic.getContent() + "')";
		Connection con = null;
		Statement smt = null;
		try {
			con = ConnectionManager.getConnection();
			smt = con.createStatement();
			smt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(smt != null){
				try {
					smt.close();
				} catch (Exception ignore) {
					// TODO: handle exception
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (Exception ignore) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * 全トピックの取得
	 */
	public List<Topic> getTopics(){
		String sql = "select * from topic";
		List<Topic> topics = new ArrayList<Topic>();

		Connection con = null;
		Statement smt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			smt = con.createStatement();
			rs = smt.executeQuery(sql);
			while(rs.next()){
				Topic topic = new Topic();
				topic.setId(rs.getInt("id"));
				topic.setPostDate(rs.getTimestamp("post_date"));
				topic.setTitle(rs.getString("title"));
				topic.setContent(rs.getString("content"));
				topics.add(topic);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (Exception ignore) {
					// TODO: handle exception
				}
			}
			if(smt != null){
				try {
					smt.close();
				} catch (Exception ignore) {
					// TODO: handle exception
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (Exception ignore) {
					// TODO: handle exception
				}
			}
		}
		return topics;
	}

	public static void main(String[] args) {
		BlogController ctrl = BlogController.getInstance();
		List<Topic> topics = ctrl.getTopics();
		for(int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i));
		}
		System.out.println("end");
	}

}
