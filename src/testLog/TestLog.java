package testLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nick:   sobolevstp
 * Date:   9/14/14
 * Time:   21:02
 *
 * @author Stepan Sobolev
 */
public class TestLog
{
	public static void main(String[] args)
	{
		Logger logger = LoggerFactory.getLogger(TestLog.class);
		logger.info("First logger output");

	}
}
