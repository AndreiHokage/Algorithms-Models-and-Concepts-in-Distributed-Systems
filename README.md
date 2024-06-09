This project is based on the book "C. Cachin, R. Guerraoui, L. Rodrigues, Introduction to Reliable and Secure Distributed Programming" and implements the fundamental algorithms 
that are applied in several distributed environments. The algorithms that have been implemented in this project are:

- Abstracting Communication
  - Perfect Links = a classic TCP connection that is openned for sending/receving a message, and afterwards is closed.
- Reliable Broadcast
  - Best Effort Braodcast (algorithm_book = BestEffortBroadcast 3.1) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/BebAbstraction.java)
- Shared Memory
  - (N, N) Atomic Register = the shared memory register is emulated in a distributed network as a message with a specific id which has to be acked by all the nodes
                           = (algorithm_book = Read-Impose Write-Consult-Majority 4.10)  [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/NnarAbstraction.java)
- Consensus
  -   Eventually Perfect Failure Detector (algorithm_book = EventualLeaderDetector  Monarchical Eventual Leader Detector 2.8) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/EPFDAbstraction.java)
  -   Eventaul Leader Detector (algorithm_book = EventuallyPerfectFailureDetector  Increasing Timeout 2.7) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/ELDAbstraction.java)
  -   Epoch Change (algorithm_book = EpochChange Leader-Based  3.1) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/ECAbstraction.java)
  -   Epoch Consensus (algorithm_book = EpochConsensus Read/Write Epoch 5.6) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/EPAbstraction.java)
  -   Uniform Consensus (algorithm_book = UniformConsensus Leader-Driven  5.7) [implementation](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/abstractizations/UCAbstraction.java)
 
  The testing environmnet is made up from an executable file received from the teacher of the lecture class, that contains 3 nodes and a hub. This repository represents an implementation
  of my own version of a node that will comunicate with the other nodes and with the hub in order to simulate a distributed environmnet. 3 instances of this project will be run
  in parallel with the other 3 instances and the hub of the teacher's exec. All the comunication is made through ProtoBuf, whose definition is found [here](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/resources/communication-protocol.proto) <br />

  The program launches multiple threads in order to be able to accept new clients, to handle new requests that belongs either to broadcast, register or consensus topics. <br />

  [SystemInputServerThread](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/servers/SystemInputServer.java) = waits for new connections and creates a worker for each received connection <br />
  
  [ProcessingInputRequestWorker](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/processing/ProcessingInputRequestWorker.java) = the above worker taht reads the lenght of the message and the content. Due to the messages within the network are slow, is possible
  as not all the bytes to reach during the first read. This is why we retry until all bytes were read successfully. All the messages are placed into either eventQueue queue or
  hearbeatQueue queue. We made a separtion between the working handling of messages and the liveness of the system. We don t want as these 2 concepts to interfere. <br />
  
  [ProcessEventQueueThread](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/servers/threadsServer/ProcessEventQueueThread.java) = uses eventQueue to pull messages, process them based on the carried abstractisation within the message description and push the deliverables back into the queue <br />
  
  [ProcessHeartBeatQueueThread](https://github.com/AndreiHokage/Algorithms-Models-and-Concepts-in-Distributed-Systems/blob/master/src/main/java/servers/threadsServer/ProcessHeartBeatQueueThread.java) = uses hearBeatQueue to pull messages (HeartBeatRequest, HeartBeatReply) and to push them back in order to be analysed by other nodes. By separating the liveness status by handling processing, we succeeds to get the actual status of the distributed system in terms of liveness. <br />

  ![alt text](https://github.com/AndreiHokage/ImageFolder/blob/main/algo_threads_desc.png) <br />
  We register our nodes abc-1, abc-2, abc-3 to the hub that already owns the teacher's nodes ref-1, ref-2, ref-3. <br />
  ![alt text](https://github.com/AndreiHokage/ImageFolder/blob/main/reg_status.png) <br />

  Our node abc-2 delivers value 45 to all 6 nodes from the system that is received by all.  <br />
  ![alt text](https://github.com/AndreiHokage/ImageFolder/blob/main/broadcast.png) <br />

  Our node abc-3 write to the register reg1 the value 100 that is acked by everyone.  <br />
  ![alt text](https://github.com/AndreiHokage/ImageFolder/blob/main/reg.png) <br />

  A consensus on topic topic1 is made. All nodes make a integer value proposal, but afterwards they choose 22 as common value.
  ![alt text](https://github.com/AndreiHokage/ImageFolder/blob/main/consensus.png) <br />

  
  

  

  
