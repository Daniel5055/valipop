/*
 * Copyright 2015 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module record_classification.
 *
 * record_classification is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * record_classification is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with record_classification. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package old.record_classification_old.tools.analysis;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import old.record_classification_old.tools.Utils;

/**
 * The Class MemoryMonitor uses {@link MemoryMXBean} to log system memory usage.
 * Memory usage is then written out to a file called "memoryUsage_" + a unique time.
 */
public class MemoryMonitor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryMonitor.class);

    /** The mxbean. */
    private MemoryMXBean mxbean;

    /** The date format. */
    private DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm");

    /** The cal. */
    private Calendar cal = Calendar.getInstance();

    /** The running. */
    private boolean running = true;

    /** The time. */
    private int time = 0;

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        while (running) {

            if (mxbean == null) {
                mxbean = ManagementFactory.getMemoryMXBean();
            }

            while (true) {
                time++;
                Utils.writeToFile(time + "\t" + mxbean.getHeapMemoryUsage().getCommitted() + "\t" + mxbean.getHeapMemoryUsage().getUsed() + "\n", "target/memoryUsage_" + cal.getTime() + ".txt", true);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e.getCause());
                }
            }
        }

    }

    /**
     * Instantiates a new memory monitor.
     */
    public MemoryMonitor() {

        //MemoryMXBean

        mxbean = ManagementFactory.getMemoryMXBean();
        LOGGER.info(dateFormat.format(cal.getTime()));
    }

    /**
     * Stop.
     */
    public void stop() {

        running = false;
    }

}
