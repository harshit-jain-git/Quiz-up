var express = require('express'), // Get the express module
    app = express(), // Create express by calling the prototype in var express
    http = require('http').Server(app), // creating a server
    io = require('socket.io')(http),
    users = {},
    socket_ids = {},
    num = 0,
    count = 0,
    updates = {};

    const PORT = process.env.PORT || 3000;
    app.get('/', (req, res) => {
      res.send('Chat Server is running on port 3000')
    });

    io.on('connection', function(socket) {
      socket.on('join', function(username) {
        console.log(username +": has joined the chat ");
        if (Object.keys(users).length == 0 || num == 1){
          users[count] = {};
          socket_ids[count] = {};
          users[count][username] = 0;
          socket_ids[count][username] = socket.id;
          num = 0;
        }
        else {
          socket_ids[count][username] = socket.id;
          users[count][username] = 0;
          num = 1;
        }

        if(Object.keys(users[count]).length==2)
        {
          updates[count]=0;
          setTimeout(sendmsg,10);
        }
      });
      function sendmsg(){
      	socket.emit('confirmation', users[count],count);
		socket.broadcast.to(Object.values(socket_ids[count])[0]).emit('confirmation', users[count],count);
        socket.broadcast.to(Object.values(socket_ids[count])[1]).emit('confirmation', users[count],count);
		console.log(users[count]);
		console.log(Object.values(socket_ids[count])[0]);
		console.log(Object.values(socket_ids[count])[1]);
		count = count + 1;
      }
      socket.on('updateResult', (score,usr,index) => {
        console.log(usr+": " +score)
        users[index][usr]=score;
        updates[index]+=1;

        if(updates[index]==2)
        {
        	socket.emit('update', users[index]);
          socket.broadcast.to(Object.values(socket_ids[index])[0]).emit('update', users[index]);
          socket.broadcast.to(Object.values(socket_ids[index])[1]).emit('update', users[index]);
          console.log(users[index]);
          updates[index]=0;
        }
      });  

      socket.on('beforedisconnect', function(index) {
      	// socket.emit('userdisconnect', users[index]);
      	console.log("hi")
        socket.broadcast.to(Object.values(socket_ids[index])[0]).emit('userdisconnect', users[index]);
        socket.broadcast.to(Object.values(socket_ids[index])[1]).emit('userdisconnect', users[index]);
      });

      socket.on('disconnect', function() {
      	var id2=socket.id;
      	var flag=false;
      	for(var key in socket_ids)
      	{
      		for(var usr in socket_ids[key])
      		{
      			if(id2.localeCompare(socket_ids[key][usr])==0)
      			{
      				flag=true;
              console.log(usr + ": has left the game")
      				socket.broadcast.to(Object.values(socket_ids[key])[0]).emit('userdisconnect', users[key]);
        			socket.broadcast.to(Object.values(socket_ids[key])[1]).emit('userdisconnect', users[key]);
      				break;
      			}
      		}
      		if(flag)
      			break;
      	}
      });
    });

    http.listen(PORT, '0.0.0.0', function() {
      console.log('listening on *:'+PORT);
    });