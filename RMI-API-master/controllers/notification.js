exports.email = (req, res) => {
  console.log(`EMAIL: warning about the sensor in floor: ${req.body.floor} and room: ${req.body.room}`);
  res.status(200).json({
    status: true,
    message: 'email warning sent success',
  });
}

exports.text = (req, res) => {
  console.log(`SMS: warning about the sensor in floor: ${req.body.floor} and room: ${req.body.room}`);

  res.status(200).json({
    status: true,
    message: 'Message sent success'
  });
}
