package me.momocow.mobasic.util;

public interface EnumToInt
{
	/**
	 * <p>Example:</p>
	 * <pre>public enum Tax {
	 *     NONE(0), SALES(10), IMPORT(5);
	 *     private final int value;
	 *     private Tax(int value) {
	 *         this.value = value;
	 *     }
	 *     
	 *     public int getValue() {
	 *         return value;
	 *     }
	 * }</pre>
	 * @see <a href='http://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int'>http://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int</a>
	 * @return
	 */
	int toInt();
}
