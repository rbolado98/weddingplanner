const mongoose=require("mongoose")
const express = require('express');
const app = express();

app.use(express.json());

mongoose.connect("mongodb://localhost:27017/weddingplanner")
.then(()=>{
    console.log("mongodb connected")
})

.catch(() => {
    console.log("fail to connect");
})

const userSchema = new mongoose.Schema({
    username: String,
    password: String
});

const User = mongoose.model('User', userSchema);

app.post('/api/login', async (req, res) => {
    const { username, password } = req.body;
    const user = await User.findOne({ username });
    if (user && user.password === password) {
      res.status(200).send({ message: 'Login successful' });
    } else {
      res.status(401).send({ message: 'Invalid credentials' });
    }
  });
  
