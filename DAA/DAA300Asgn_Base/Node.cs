using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// This class represents each of the nodes of the Huffman Tree. It has
    /// fields for left/right nodes, the symbol (if it's a leaf) and the
    /// frequency of this symbol.
    public class Node
    {
        private Node left;
        private Node right;
        private char symbol;
        private int frequency;

        /// Default constructor
        public Node()
        {
            left = null;
            right = null;
            frequency = 0;
            symbol = '!';
        }

        /// Setters & Getters
        public void SetSymbol(char c)
        {
            symbol = c;
        }

        public void SetLeft(Node n)
        {
            left = n;
        }

        public void SetRight(Node n)
        {
            right = n;
        }

        public void SetFreq(int i)
        {
            frequency = i;
        }

        public char GetSymbol()
        {
            return symbol;
        }

        public Node GetLeft()
        {
            return left;
        }

        public Node GetRight()
        {
            return right;
        }

        public int GetFreq()
        {
            return frequency;
        }

        /// Determines whether the Node is a branch or a leaf. This is
        /// important for part of the Huffman Algorithm.
        public bool IsBranch()
        {
            if ((left == null) && (right == null))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
