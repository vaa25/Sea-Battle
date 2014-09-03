package model.interfaces;

import model.enums.ShotResult;

import java.awt.*;

/**
 * Nick:   sobolevstp
 * E-mail: sobolev.stp@gmail.com
 * Date:   8/29/14
 * Time:   17:19
 *
 * @author Stepan Sobolev
 */
public interface Shotable
{
	public ShotResult getShot(Point p);
}