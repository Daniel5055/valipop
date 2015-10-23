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
package uk.ac.standrews.cs.digitising_scotland.record_classification.cli;

import java.util.logging.*;

/**
 * @author Masih Hajiarab Derkani
 */
class CLIConsoleHandler extends ConsoleHandler {

    public static final Formatter CLI_CONSOLE_LOG_FORMATTER = new Formatter() {

        @Override
        public String format(final LogRecord record) {

            return String.format("%-7s %s%n", record.getLevel().toString(), record.getMessage());
        }
    };

    public CLIConsoleHandler() {

        super();
        setFormatter(CLI_CONSOLE_LOG_FORMATTER);
    }

    //TODO publish severe log level through stderr
}
