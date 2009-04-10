//////////////////////////////////////////////////
// JIST (Java In Simulation Time) Project
// Timestamp: <Timer.java Tue 2004/04/06 11:46:57 barr pompom.cs.cornell.edu>
//

// Copyright (C) 2004 by Cornell University
// All rights reserved.
// Refer to LICENSE for terms and conditions of use.

package jist.swans.misc;

import jist.runtime.JistAPI;

/**
 * Timer expiration interface.
 *
 * @author Rimon Barr &lt;barr+jist@cs.cornell.edu&gt;
 * @version $Id: Timer.java,v 1.1 2007/04/09 18:49:19 drchoffnes Exp $
 * @since SWANS1.0
 */
public interface Timer
{

  /**
   * Timer expiration processing.
   */
  void timeout();
  
  public interface ReusableTimer extends JistAPI.Proxiable
  {
      void timeout(int idVal);
  }

} // interface: Timer
