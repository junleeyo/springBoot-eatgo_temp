"use strict";

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

_asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
  var url, response, restaurants, element;
  return regeneratorRuntime.wrap(function _callee$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          url = 'http://localhost:8080/restaurants';
          _context.next = 3;
          return fetch(url);

        case 3:
          response = _context.sent;
          _context.next = 6;
          return response.json();

        case 6:
          restaurants = _context.sent;
          //console.log(restaurants);
          element = document.getElementById('app');
          element.innerHTML = "\n        ".concat(restaurants.map(function (restaurant) {
            return "\n            <p>\n                ".concat(restaurant.id, "\n                ").concat(restaurant.name, "\n                ").concat(restaurant.address, "\n            </p>\n        ");
          }).join(''), "\n    "); //JSON.stringify(restaurants);

        case 9:
        case "end":
          return _context.stop();
      }
    }
  }, _callee);
}))();