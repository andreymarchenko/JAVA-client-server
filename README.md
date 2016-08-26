# JAVA-client-server

https://docs.oracle.com/javase/8/docs/api/java/lang/ProcessBuilder.html
https://docs.oracle.com/javase/8/docs/api/java/lang/Process.html

http://cybern.ru/urok-23-bibliotechnye-komponenty-priorityblockingqueue.html

Требуются следующие улучшения:

1. Создать Logger.java, создать класс Logger и написать ф-цию AddToLog. В остальных файлах заинклюдить этот файл и использовать написанную ф-цию AddToLog вместо того, чтобы каждый раз писать реализацию этой функции в остальных классах. :tada:
2. Написать на стороне клиента ф-цию HandlerRequestOfClient (по аналогии HandlerOfClient в классе RecvThread на стороне сервера) :tada:
3. Исправить Disconnect на клиенте.
4. Избавиться от костыля в QueueHandlerThread (метод run). Идея следующая:

   4.1. Избавиться от использования HashTable. Достаточно иметь расшаренную структуру PriorityBlockQueue.

   4.2. В PriorityBlockQueue добавлять структуру, которая имеет следующую информацию: сокет клиента, его задача.
   
   4.3. PriorityBlockQueue не должен находиться в активном ожидании. Для этого RecvThread каждого клиента после получения задачи должен залочить доступ к PriorityBlockQueue и добавить задачу в расшаренную PriorityBlockQueue, а также пробудить QueueHandlerThread поток (но возможно он уже будет пробужден). На стороне QueueHandlerThread логика такая: после своего пробуждения исполняется метод run до тех пор, пока очередь задач не опустеет. Если очередь стала пуста, то поток QueueHandlerThread делает sleep. Проверка пустоты очереди должна лочить мьютекс, который отвечает за синхронизированность сохранения данных в PriorityBlockQueue
