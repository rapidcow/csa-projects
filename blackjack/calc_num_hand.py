import math
import unittest

BLACKJACK = 21

def get_num_hand_naive(card_sum, ace_count):
    num_hand = card_sum + 11*ace_count
    eleven_ace = ace_count
    while eleven_ace:
        if num_hand <= BLACKJACK:
            return True, num_hand
        num_hand -= 10
        eleven_ace -= 1
    return False, num_hand


def get_num_hand_mathy(card_sum, ace_count):
    num_hand = card_sum + ace_count
    diff = max(0, BLACKJACK - num_hand)
    is_soft = diff >= 10
    # simulate C/Java truncated division
    num_hand += math.trunc(diff / 10) * 10
    return is_soft, num_hand


class TestGetNumHand(unittest.TestCase):
    def test1(self):
        for f in (get_num_hand_naive, get_num_hand_mathy):
            self.assertEqual(f(13, 0), (False, 13))
            self.assertEqual(f(2, 1), (True, 13))
            self.assertEqual(f(10, 1), (True, 21))
            self.assertEqual(f(11, 1), (False, 12))
            self.assertEqual(f(20, 1), (False, 21))
            # bust
            self.assertEqual(f(21, 1), (False, 22))
            # too many aces
            self.assertEqual(f(0, 2), (True, 12))
            self.assertEqual(f(0, 3), (True, 13))
            self.assertEqual(f(1, 2), (True, 13))


if __name__ == '__main__':
    unittest.main()
