package hr.fer.zemris.java.hw05.db;

/**
 * Represents record for one student. Two students are equal if their JMBAGs are
 * equal. Multiple records for the same students can not exist.
 * 
 * @author MarinoK
 */
public class StudentRecord {

	/** Student's unique identification number. */
	private String jmbag;

	/** Student's last name. */
	private String lastName;

	/** Student's first name. */
	private String firstName;

	/** Student's grade. */
	private int finalGrade;

	/**
	 * Constructor for the StudentRecord.
	 * 
	 * @param jmbag
	 *            of this student
	 * @param firstName
	 *            of this student
	 * @param lastName
	 *            of this student
	 * @param finalGrade
	 *            of this student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for the JMBAG of the student.
	 * 
	 * @return JMBAG, as a string
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the first name of the student.
	 * 
	 * @return first name, as a string
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for the last name of the student.
	 * 
	 * @return last name, as a string
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for the final grade of the student.
	 * 
	 * @return final grade, as a integer
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) return false;
		} else if (!jmbag.equals(other.jmbag)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag).append(lastName).append(firstName).append(finalGrade);
		return sb.toString();
	}

}
