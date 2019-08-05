/*
Word Anagram

Input : a set of input characters, ranging from a-z, case-insensitive.
Output: all the possible English words from the dictionary that could be composed of these
        characters, case-insensitive.

Method:
1. Using Trie to store the word dictionary. Each node of the trie is an array of 26.
2. Using recursion, check whether the node index is null or not. If is not null, then continue
   searching.
3. !! Notice: each recursion shall exclude the characters been checked to avoid repetition use of
   characters.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solution {


    /**************** Word Anagram ****************/
    static Trie dict = new Trie();
    static ArrayList<String> possibleWords = new ArrayList<>();

    public static void possibleWord(List<Character> chars, TrieNode current, String str) {
        if (current.isLeaf) possibleWords.add(str);

        for (int i = 0; i < chars.size(); i++) {
            char c = Character.toLowerCase(chars.get(i));
            int index = (int) c-'a';

            if (current.node[index] != null ) {
                // new list does not contain this character.
                List<Character> list = new ArrayList<>(chars.subList(i+1, chars.size()));
                list.addAll(chars.subList(0, i));

                possibleWord(list, current.node[index],str + c);
            }
        }
    }

    public static void wordAnagram(List<Character> chars) {
        if (chars.size() == 0) return;

        TrieNode root = dict.root;

        for (int i = 0; i < chars.size(); i++) {
            String str = "";
            char c = Character.toLowerCase(chars.get(i));
            int index = (int) c-'a';

            if (root.node[index] != null) {
                // new list does not contain this character.
                List<Character> list = new ArrayList<>(chars.subList(i+1, chars.size()));
                list.addAll(chars.subList(0, i));

                possibleWord(list, root.node[index], str+c);
            }
        }
    }

    public static void main(String[] args) {
        /************ word dictionary ************/
        String dict_file = "/usr/share/dict/words";

        // Read the dictionary
        System.out.println("Reading the dictionary " + dict_file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(dict_file));
            String str;
            while ((str = br.readLine()) != null) {
                if (!(str != null && str.matches("^[a-zA-Z]*$"))) // delete words with non-alphabet letters.
                    continue;
                dict.insert(str.toLowerCase());
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(dict.search("mg"));

        /************ test ************/
        List<Character> chars = new ArrayList<>();
        chars.add('e');
//        chars.add('b');
//        chars.add('a');
//        chars.add('o');
        chars.add('g');
        chars.add('m');
        chars.add('l');

        wordAnagram(chars);

        for (String s: possibleWords) {
            System.out.println(s);
        }
        System.out.println(possibleWords.size());
    }
}

/**************** Trie ****************/
class TrieNode {
    TrieNode[] node = new TrieNode[26];
    boolean isLeaf; // true if the word ends at this point.

    public TrieNode() {
        isLeaf = false;
        Arrays.fill(node, null);
    }
}

class Trie {
    public TrieNode root;
    public Trie() {
        root = new TrieNode();
    }


    public void insert(String word) {
        if(search(word) == true) return;

        TrieNode current = root;
        for(int i = 0; i < word.length(); i++){
            int index = word.charAt(i) - 'a';
            if (current.node[index] == null)
                current.node[index] = new TrieNode();

            current = current.node[index];
        }
        // Set isLeaf to indicate end of the word
        current.isLeaf = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (current.node[index] == null)
                return false;
            else
                current = current.node[index];
        }

        if (current.isLeaf) return true;
        else return false;
    }
}

