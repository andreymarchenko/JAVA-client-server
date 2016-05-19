/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Comparator;

/**
 *
 * @author Игорь
 */
public class ComparatorPriorityTask implements Comparator<ComparatorPriorityTask> {
    public int priority;
    
    ComparatorPriorityTask(int _priority) {
        priority = _priority;
    }

    @Override
    public int compare(ComparatorPriorityTask CPT_1, ComparatorPriorityTask CPT_2) {
        if (CPT_1.priority > CPT_2.priority) {
            return 1;
        } else if (CPT_1.priority < CPT_2.priority) {
            return -1;
        } else {
            return 0;
        }

    }
}
