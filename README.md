## BSTree Client(GUI), Server(CLI)

### Build
```bash
> make
```

### Usage

- Start Server
```bash
> startServer
```

- Start Client
```bash
> startClient
```

### Client Server connection
Server is listening on 4444(tcp) port.
To test connection to the Server (using CLI):
```bash
console1> ./startServer
Turning on the server
New client connected

console2> telnet localhost 4444
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
Integer
insert
1
1(())(())
insert
2
1(())(2(())(()))
draw
1(())(2(())(()))
search
2
We found element: 2 inside the tree!
delete
2
1(())(())
draw
1(())(())
```
