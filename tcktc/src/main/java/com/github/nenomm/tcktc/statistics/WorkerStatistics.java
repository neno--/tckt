package com.github.nenomm.tcktc.statistics;

import com.github.nenomm.tckt.lib.Ticket;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerStatistics {
    private StopWatch stopWatch;
    private StopWatch commonStopWatch;

    private ArrayList tickets;
    private ArrayList commonTickets;

    public WorkerStatistics(long numberOfTickets) {
        stopWatch = new StopWatch();
        commonStopWatch = new StopWatch();

        // constructor only supports int for initial size
        tickets = new ArrayList((int) Math.min(numberOfTickets, Integer.MAX_VALUE));
        commonTickets = new ArrayList((int) Math.min(numberOfTickets, Integer.MAX_VALUE));
    }

    public void start() {
        stopWatch.start();
    }

    public void lap(Ticket ticket) {
        stopWatch.stop();
        tickets.add(ticket);
    }

    public void startCommon() {
        commonStopWatch.start();
    }

    public void lapCommon(Ticket ticket) {
        commonStopWatch.stop();
        commonTickets.add(ticket);
    }

    public StopWatch getClientStatistics() {
        checkIsClientRunning();
        return stopWatch;
    }

    public StopWatch getCommonStatistics() {
        checkIsCommonRunning();
        return commonStopWatch;
    }

    public List getClientTickets() {
        checkIsClientRunning();
        return Collections.unmodifiableList(tickets);
    }

    public List getCommonTickets() {
        checkIsCommonRunning();
        return Collections.unmodifiableList(commonTickets);
    }

    private void checkIsClientRunning() {
        if (stopWatch.isRunning()) {
            throw new IllegalStateException("Client statistics are still running!");
        }
    }

    private void checkIsCommonRunning() {
        if (commonStopWatch.isRunning()) {
            throw new IllegalStateException("Common statistics are still running!");
        }
    }
}
