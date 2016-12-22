/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Comparator;
import server.BlockInstance;

/**
 *
 * @author Игорь
 */
public class ComparatorPriorityTask implements Comparable<ComparatorPriorityTask> {

    public BlockInstance BI;

    ComparatorPriorityTask(BlockInstance _BI) {
        BI = _BI;
    }

    @Override
    public int compareTo(ComparatorPriorityTask CPT) {
        if (CPT.BI.priority > BI.priority) {
            return 1;
        } else if (CPT.BI.priority < BI.priority) {
            return -1;
        } else {
            return 0;
        }
    }
}
