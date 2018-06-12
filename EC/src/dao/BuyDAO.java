package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import base.DBManager;
import beans.BuyDataBeans;
import beans.DeliveryMethodDataBeans;

public class BuyDAO {
	//インスタンスオブジェクトを返却させてコードの簡略化
	public static BuyDAO getInstance() {
		return new BuyDAO();
	}

	/**
	 * 購入情報登録処理
	 * @param bdb 購入情報
	 * @throws SQLException 呼び出し元にスローさせるため
	 */
	public static int insertBuy(BuyDataBeans bdb) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		int autoIncKey = -1;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO t_buy(user_id,total_price,delivery_method_id,create_date) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, bdb.getUserId());
			st.setInt(2, bdb.getTotalPrice());
			st.setInt(3, bdb.getDelivertMethodId());
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				autoIncKey = rs.getInt(1);
			}
			System.out.println("inserting buy-datas has been completed");

			return autoIncKey;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 購入IDによる購入情報検索
	 * @param buyId
	 * @return BuyDataBeans
	 * 				購入情報のデータを持つJavaBeansのリスト
	 * @throws SQLException
	 * 				呼び出し元にスローさせるため
	 */
	public static BuyDataBeans getBuyDataBeansByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT total_price, delivery_method_id, create_date "
					+ "FROM ec_db.t_buy "
					+ "WHERE t_buy.id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();


			if(!(rs.next())) {
				return null;
			}
				int totalPrice = (rs.getInt("total_price"));
				int delivertMethodId = (rs.getInt("delivery_method_id"));
				Date buyDate = (rs.getTimestamp("create_date"));

				DeliveryMethodDataBeans delivery = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(delivertMethodId);
				String deliveryMethodName = delivery.getName();
				int deliveryMethodPrice = delivery.getPrice();

				BuyDataBeans resultBDB = new BuyDataBeans(totalPrice,buyDate,deliveryMethodPrice,deliveryMethodName);

				return resultBDB;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<BuyDataBeans> boughtData(int userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		ArrayList<BuyDataBeans> boughtList = new ArrayList<BuyDataBeans>();
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"select * from t_buy where user_id = ?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = (rs.getInt("id"));
				int totalPrice = (rs.getInt("total_price"));
				Date buyDate = (rs.getTimestamp("create_date"));
				int delivertMethodId = (rs.getInt("delivery_method_id"));
				int boughtUserId = (rs.getInt("user_id"));

				DeliveryMethodDataBeans deliName = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(delivertMethodId);
				String deliveryMethodName = deliName.getName();

				BuyDataBeans bought = new BuyDataBeans(id,totalPrice,buyDate,deliveryMethodName,boughtUserId);

				boughtList.add(bought);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return boughtList;
	}


}
