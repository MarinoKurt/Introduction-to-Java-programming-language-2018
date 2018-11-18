package hr.fer.zemris.java.hw06.demo4;

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

	/** Points this student achieved through mid exam. */
	private Double pointsMI;

	/** Points this student achieved through final exam. */
	private Double pointsZI;

	/** Points this student achieved through laboratory exercises. */
	private Double pointsLAB;

	/** Student's grade. */
	private Integer finalGrade;

	/**
	 * Constructor for the StudentRecord.
	 * 
	 * @param jmbag
	 *            of this student
	 * @param firstName
	 *            of this student
	 * @param lastName
	 *            of this student
	 * @param pointsMI
	 *            of this student
	 * @param pointsZI
	 *            of this student
	 * @param pointsLAB
	 *            of this student
	 * @param finalGrade
	 *            of this student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, Double pointsMI, Double pointsZI,
			Double pointsLAB, Integer finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLAB = pointsLAB;
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
	 * Getter for the MI points of the student.
	 * 
	 * @return points from MI
	 */
	public Double getPointsMI() {
		return pointsMI;
	}

	/**
	 * Getter for the ZI points of the student.
	 * 
	 * @return points from ZI
	 */
	public Double getPointsZI() {
		return pointsZI;
	}

	/**
	 * Getter for the LAB points of the student.
	 * 
	 * @return points from LAB
	 */
	public Double getPointsLAB() {
		return pointsLAB;
	}

	/**
	 * Getter for the sum of the points.
	 * 
	 * @return sum of the points of this student
	 */
	public Double getTotalPoints() {
		return pointsLAB + pointsMI + pointsZI;
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
		sb.append(jmbag)
				.append(" ")
				.append(lastName)
				.append(" ")
				.append(firstName)
				.append(" ")
				.append(pointsMI)
				.append(" ")
				.append(pointsZI)
				.append(" ")
				.append(pointsLAB)
				.append(" ")
				.append(finalGrade);
		return sb.toString();
	}

}
