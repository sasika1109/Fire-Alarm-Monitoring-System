const User = require('./../models/User');

// auth controller
exports.checkAuth = async (req, res) => {
  const user = await User.findOne({email: req.body.email});

  // if there is no user, response with the following
  if (!user) {
    res.status(200).json({
      message: 'unauthorized',
    });
    return;
  }
	// if there is s user, validate the password the provided password
  if (req.body.password == user.password) {
    res.status(200).json({
      message: 'authorized',
    });
  } else {
    res.status(200).json({
      message: 'unauthorized',
    });
  }
}
