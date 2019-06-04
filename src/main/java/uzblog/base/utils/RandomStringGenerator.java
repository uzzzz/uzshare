
package uzblog.base.utils;

public interface RandomStringGenerator {

	int getMinLength();

	int getMaxLength();

	String getNewString();

	byte[] getNewStringAsBytes();
}
