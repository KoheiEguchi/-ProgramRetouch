package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import base.DBManager;
import beans.BuyDetailDataBeans;
import beans.DeliveryMethodDataBeans;
import beans.ItemDataBeans;

public class BuyDetailDAO {
	//インスタンスオブジェクトを返却させてコードの簡略化
	public static BuyDetailDAO getInstance() {
		return new BuyDetailDAO();
	}

	/**
	 * 購入詳細登録処理
	 * @param bddb BuyDetailDataBeans
	 * @throws SQLException
	 * 			呼び出し元にスローさせるため
	 */
	public static void insertBuyDetail(BuyDetailDataBeans bddb) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO t_buy_detail(buy_id,item_id) VALUES(?,?)");
			st.setInt(1, bddb.getBuyId());
			st.setInt(2, bddb.getItemId());
			st.executeUpdate();
			System.out.println("inserting BuyDetail has been completed");

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
	 * @return {BuyDataDetailBeans}
	 * @throws SQLException
	 */
	public ArrayList<BuyDetailDataBeans> getBuyDataBeansListByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM t_buy_detail WHERE buy_id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();
			ArrayList<BuyDetailDataBeans> buyDetailList = new ArrayList<BuyDetailDataBeans>();

			while (rs.next()) {
				BuyDetailDataBeans bddb = new BuyDetailDataBeans();
				bddb.setId(rs.getInt("id"));
				bddb.setBuyId(rs.getInt("buy_id"));
				bddb.setItemId(rs.getInt("item_id"));
				buyDetailList.add(bddb);
			}

			System.out.println("searching BuyDataBeansList by BuyID has been completed");
			return buyDetailList;
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
     * 購入IDによる購入詳細情報検索
     * @param buyId
     * @return buyDetailItemList ArrayList<ItemDataBeans>
     *             購入詳細情報のデータを持つJavaBeansのリスト
     * @throws SQLException
     */
	public static ArrayList<ItemDataBeans> getItemDataBeansListByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		ArrayList<ItemDataBeans> buyIDBList = new ArrayList<ItemDataBeans>();
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT m_item.id,"
					+ " m_item.name,"
					+ " m_item.price"
					+ " FROM t_buy_detail"
					+ " JOIN m_item"
					+ " ON t_buy_detail.item_id = m_item.id"
					+ " WHERE t_buy_detail.buy_id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();


			while (rs.next()) {
				int id = (rs.getInt("id"));
				String name = (rs.getString("name"));
				int price = (rs.getInt("price"));

				ItemDataBeans idb = new ItemDataBeans(id,name,price);

				buyIDBList.add(idb);
			}

			System.out.println("searching ItemDataBeansList by BuyID has been completed");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return buyIDBList;
	}

		public static ItemDataBeans boughtDetail(String checkId) throws SQLException {
			Connection con = null;
			PreparedStatement st = null;
			try {
				con = DBManager.getConnection();

				st = con.prepareStatement(
						"SELECT total_price, delivery_method_id, create_date"
						+ " FROM ec_db.t_buy"
						+ " WHERE t_buy.id = ?");
				st.setString(1, checkId);

				ResultSet rs = st.executeQuery();

				if (!(rs.next())) {
					return null;
				}

					int totalPrice = (rs.getInt("total_price"));
					Date buyDate = (rs.getTimestamp("create_date"));
					int delivertMethodId = (rs.getInt("delivery_method_id"));

					DeliveryMethodDataBeans delivery = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(delivertMethodId);
					String deliveryMethodName = delivery.getName();
					int deliveryMethodPrice = delivery.getPrice();
					ItemDataBeans boughtDetail1 = new ItemDataBeans(totalPrice,buyDate,deliveryMethodName,deliveryMethodPrice);

				System.out.println("searching ItemDataBeansList by BuyID has been completed");
				return boughtDetail1;

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new SQLException(e);
			} finally {
				if (con != null) {
					con.close();
				}
			}

		}

		public static ArrayList<ItemDataBeans> boughtListData(String checkId) throws SQLException {
			Connection con = null;
			PreparedStatement st = null;
			ArrayList<ItemDataBeans> boughtDetailList2 = new ArrayList<ItemDataBeans>();
			try {
				con = DBManager.getConnection();

				st = con.prepareStatement(
						"SELECT DISTINCT name,price "
						+ "FROM ec_db.m_item "
						+ "JOIN ec_db.t_buy_detail "
						+ "ON m_item.id = t_buy_detail.item_id "
						+ "WHERE item_id IN"
						+ " (SELECT item_id "
						+ "FROM ec_db.t_buy_detail "
						+ "WHERE id IN"
						+ " (SELECT id "
						+ "FROM ec_db.t_buy_detail "
						+ "WHERE buy_id = ?)"
						+" )");
				st.setString(1, checkId);

				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					ItemDataBeans boughtDetail2 = new ItemDataBeans();
					boughtDetail2.setName(rs.getString("name"));
					boughtDetail2.setPrice(rs.getInt("price"));

					boughtDetailList2.add(boughtDetail2);
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new SQLException(e);
			} finally {
				if (con != null) {
					con.close();
				}
			}
			return boughtDetailList2;
		}

}
