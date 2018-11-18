package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Implementation of the DAO layer using SQL technology. Expects a connection
 * from the SQLConnectionProvider.
 * 
 * @author MarinoK
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getInt(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading polls.", ex);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading a poll.", ex);
		}
		return poll;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=? order by id");
			pst.setLong(1, Long.valueOf(pollID));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getInt(5));
						pollOptions.add(pollOption);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading polls.", ex);
		}
		return pollOptions;
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption pollOption = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getInt(5));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading polls.", ex);
		}
		return pollOption;
	}

}