/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Comparator;
import server.BlockInstance;

/**
 *
 * @author Игорь
 */
public class ComparatorPriorityTask implements Comparator<ComparatorPriorityTask> {

    public BlockInstance BI;

    ComparatorPriorityTask(BlockInstance _BI) {
        BI = _BI;
    }

    public int compare(ComparatorPriorityTask CPT_1, ComparatorPriorityTask CPT_2) {
        if (CPT_1.BI.priority > CPT_2.BI.priority) {
            return 1;
        } else if (CPT_1.BI.priority < CPT_2.BI.priority) {
            return -1;
        } else {
            return 0;
        }

    }
}
