package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Contract designed to provide a communication between the database and the
 * application, in this case to fetch the poll information from the database.
 * 
 * @author MarinoK
 */
public interface DAO {

	/**
	 * Fetches the polls from the database.
	 * 
	 * @return list of polls
	 * @throws DAOException
	 *             if there is a problem with reading from the database
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Fetches a poll from the database.
	 * 
	 * @param id
	 *            of the poll
	 * 
	 * @return poll under the given id
	 * @throws DAOException
	 *             if there is a problem with reading from the database
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * Fetches all poll options in the database under the given parent poll ID.
	 * 
	 * @param pollID
	 *            of the parent poll
	 * 
	 * @return list of poll options
	 * @throws DAOException
	 *             if there is a problem with reading from the database
	 */
	public List<PollOption> getPollOptions(long pollID) throws DAOException;

	/**
	 * Fetches the poll option in the database under the given ID.
	 * 
	 * @param id
	 *            of the poll option
	 * 
	 * @return poll option under the given ID
	 * @throws DAOException
	 *             if there is a problem with reading from the database
	 */
	public PollOption getPollOption(long id) throws DAOException;
}