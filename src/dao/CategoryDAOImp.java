package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionDB;
import entity.Category;

public class CategoryDAOImp implements ICategory {

	private Connection connection;
	
	public CategoryDAOImp() {
		this.connection = ConnectionDB.getConnection();
	}

	@Override
	public List<Category> getAll() {
		List<Category> list = new ArrayList<>();
		Statement stt = null;
		ResultSet rs = null;
		String sql = "select * from category";
		try {
			stt = connection.createStatement();
			rs = stt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getNString("name");
				boolean status = rs.getInt("status") == 1 ? true : false;
				String parentId = rs.getString("parentId");
				Category category = new Category(id, name, status, parentId);
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stt != null) {
				try {
					stt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public boolean addCat(Category c) {
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall("{CALL spInsertCate(?,?,?,?)}");
			callableStatement.setString(1, c.getId());
			callableStatement.setNString(2, c.getName());
			callableStatement.setInt(3, c.getStatus() ? 1 : 0);
			callableStatement.setString(4, c.getParentId());
			if (callableStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public Category getById(String _id) {
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		Category category = null;
		try {
			callableStatement = connection.prepareCall("{CALL spCateById(?)}");
			callableStatement.setString(1, _id);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getNString("name");
				boolean status = rs.getInt("status") == 1 ? true : false;
				String parentId = rs.getString("parentId");
				category = new Category(id, name, status, parentId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return category;
	}

	@Override
	public boolean deleteCat(String id) {
		boolean deleted = false;
		CallableStatement callableStatement = null;
		try {
			if (getTotalProduct(id) == 0) {
				callableStatement = connection.prepareCall("{CALL spDeleteCate(?)}");
				callableStatement.setString(1, id);
				if (callableStatement.executeUpdate() > 0) {
					deleted = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return deleted;
	}

	@Override
	public List<Category> searchByName(String _name) {
		List<Category> list = new ArrayList<>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spSearchCateByName(?)}");
			callableStatement.setNString(1,_name);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getNString("name");
				boolean status = rs.getInt("status") == 1 ? true : false;
				String parentId = rs.getString("parentId");
				Category c = new Category(id, name, status, parentId);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public String getParentName(String id) {
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		String parentName = null;
		try {
			callableStatement = connection.prepareCall("{CALL spGetNameCate(?)}");
			callableStatement.setString(1, id);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				parentName = rs.getNString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return parentName;
	}

	@Override
	public int getTotalProduct(String id) {
		int total = 0;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = connection.prepareCall("{CALL spGetTotalProduct(?)}");
			callableStatement.setString(1, id);
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
