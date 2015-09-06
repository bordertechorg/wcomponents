package com.github.bordertech.wcomponents.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WhiteSpaceFilterOutputStream is a state machine that filters 
 * out extraneous whitespace from XHTML content.
 * 
 * @author Yiannis Paschalidis
 * @since 1.0.0
 */    
public class WhiteSpaceFilterStateMachine
{
    private static final State STATE_TRIM_WHITESPACE = new State();
    private static final State STATE_IN_TEXT = new State();        
    private static final State STATE_START_TAG = new State();

    private static final State STATE_START_COMMENT1 = new State();
    private static final State STATE_START_COMMENT2 = new State();
    private static final State STATE_IN_COMMENT = new State();
    private static final State STATE_END_COMMENT1 = new State();
    private static final State STATE_END_COMMENT2 = new State();
    
    private static final State STATE_START_CDATA1 = new State();
    private static final State STATE_START_CDATA2 = new State();
    private static final State STATE_START_CDATA3 = new State();
    private static final State STATE_START_CDATA4 = new State();
    private static final State STATE_START_CDATA5 = new State();
    private static final State STATE_START_CDATA6 = new State();
    private static final State STATE_CDATA_TEXT = new State();
    private static final State STATE_END_CDATA1 = new State();
    private static final State STATE_END_CDATA2 = new State();
    
    private static final State STATE_START_SCRIPT1 = new State();
    private static final State STATE_START_SCRIPT2 = new State();
    private static final State STATE_START_SCRIPT3 = new State();
    private static final State STATE_START_SCRIPT4 = new State();
    private static final State STATE_START_SCRIPT5 = new State();
    private static final State STATE_START_SCRIPT6 = new State();
    private static final State STATE_IN_SCRIPT_TAG = new State();
    private static final State STATE_SCRIPT_ATTR1 = new State();
    private static final State STATE_SCRIPT_ATTR2 = new State();
    private static final State STATE_SCRIPT_TEXT = new State();
    private static final State STATE_END_SCRIPT1 = new State();
    private static final State STATE_END_SCRIPT2 = new State();
    private static final State STATE_END_SCRIPT3 = new State();
    private static final State STATE_END_SCRIPT4 = new State();
    private static final State STATE_END_SCRIPT5 = new State();
    private static final State STATE_END_SCRIPT6 = new State();
    private static final State STATE_END_SCRIPT7 = new State();
    private static final State STATE_END_SCRIPT8 = new State();
    
    private static final State STATE_IN_OTHER_TAG = new State();
    private static final State STATE_OTHER_TAG_ATTR1 = new State();
    private static final State STATE_OTHER_TAG_ATTR2 = new State();
    
    private static final State STATE_START_PRE1 = new State();
    private static final State STATE_START_PRE2 = new State();
    private static final State STATE_START_PRE3 = new State();        
    private static final State STATE_IN_PRE_TAG = new State();
    private static final State STATE_PRE_TEXT = new State();
    private static final State STATE_PRE_TAG_ATTR1 = new State();
    private static final State STATE_PRE_TAG_ATTR2 = new State();
    private static final State STATE_END_PRE1 = new State();
    private static final State STATE_END_PRE2 = new State();
    private static final State STATE_END_PRE3 = new State();
    private static final State STATE_END_PRE4 = new State();
    private static final State STATE_END_PRE5 = new State();
    
    private static final State STATE_START_TEXTAREA1 = new State();
    private static final State STATE_START_TEXTAREA2 = new State();
    private static final State STATE_START_TEXTAREA3 = new State();
    private static final State STATE_START_TEXTAREA4 = new State();
    private static final State STATE_START_TEXTAREA5 = new State();
    private static final State STATE_START_TEXTAREA6 = new State();
    private static final State STATE_START_TEXTAREA7 = new State();
    private static final State STATE_START_TEXTAREA8 = new State();
    private static final State STATE_IN_TEXTAREA_TAG = new State();
    private static final State STATE_TEXTAREA_TAG_ATTR1 = new State();
    private static final State STATE_TEXTAREA_TAG_ATTR2 = new State();
    private static final State STATE_TEXTAREA_TEXT = new State();
    private static final State STATE_END_TEXTAREA1 = new State();
    private static final State STATE_END_TEXTAREA2 = new State();
    private static final State STATE_END_TEXTAREA3 = new State();
    private static final State STATE_END_TEXTAREA4 = new State();
    private static final State STATE_END_TEXTAREA5 = new State();
    private static final State STATE_END_TEXTAREA6 = new State();
    private static final State STATE_END_TEXTAREA7 = new State();
    private static final State STATE_END_TEXTAREA8 = new State();
    private static final State STATE_END_TEXTAREA9 = new State();
    private static final State STATE_END_TEXTAREA10 = new State();

    private static final State STATE_START_UI1 = new State();
    private static final State STATE_START_UI2 = new State();
    private static final State STATE_START_UI3 = new State();

    private static final State STATE_START_UI_TEXT1 = new State();
    private static final State STATE_START_UI_TEXT2 = new State();
    private static final State STATE_START_UI_TEXT3 = new State();
    private static final State STATE_START_UI_TEXT4 = new State();
    private static final State STATE_IN_UI_TEXT_TAG = new State();
    private static final State STATE_UI_TEXT_TAG_ATTR1 = new State();
    private static final State STATE_UI_TEXT_TAG_ATTR2 = new State();
    private static final State STATE_UI_TEXT_TEXT = new State();
    private static final State STATE_END_UI_TEXT1 = new State();
    private static final State STATE_END_UI_TEXT2 = new State();
    private static final State STATE_END_UI_TEXT3 = new State();
    private static final State STATE_END_UI_TEXT4 = new State();
    private static final State STATE_END_UI_TEXT5 = new State();
    private static final State STATE_END_UI_TEXT6 = new State();
    private static final State STATE_END_UI_TEXT7 = new State();
    private static final State STATE_END_UI_TEXT8 = new State();
    private static final State STATE_END_UI_TEXT9 = new State();
    
    private static final State STATE_START_UI_TEXTAREA5 = new State();
    private static final State STATE_START_UI_TEXTAREA6 = new State();
    private static final State STATE_START_UI_TEXTAREA7 = new State();
    private static final State STATE_START_UI_TEXTAREA8 = new State();
    private static final State STATE_IN_UI_TEXTAREA_TAG = new State();
    private static final State STATE_UI_TEXTAREA_TAG_ATTR1 = new State();
    private static final State STATE_UI_TEXTAREA_TAG_ATTR2 = new State();
    private static final State STATE_UI_TEXTAREA_TEXT = new State();
    private static final State STATE_END_UI_TEXTAREA1 = new State();
    private static final State STATE_END_UI_TEXTAREA2 = new State();
    private static final State STATE_END_UI_TEXTAREA3 = new State();
    private static final State STATE_END_UI_TEXTAREA4 = new State();
    private static final State STATE_END_UI_TEXTAREA5 = new State();
    private static final State STATE_END_UI_TEXTAREA6 = new State();
    private static final State STATE_END_UI_TEXTAREA7 = new State();
    private static final State STATE_END_UI_TEXTAREA8 = new State();
    private static final State STATE_END_UI_TEXTAREA9 = new State();
    private static final State STATE_END_UI_TEXTAREA10 = new State();
    private static final State STATE_END_UI_TEXTAREA11 = new State();
    private static final State STATE_END_UI_TEXTAREA12 = new State();
    private static final State STATE_END_UI_TEXTAREA13 = new State();

    private static final State STATE_START_UI_MESSAGE1 = new State();
    private static final State STATE_START_UI_MESSAGE2 = new State();
    private static final State STATE_START_UI_MESSAGE3 = new State();
    private static final State STATE_START_UI_MESSAGE4 = new State();
    private static final State STATE_START_UI_MESSAGE5 = new State();
    private static final State STATE_START_UI_MESSAGE6 = new State();
    private static final State STATE_START_UI_MESSAGE7 = new State();
    private static final State STATE_IN_UI_MESSAGE_TAG = new State();
    private static final State STATE_UI_MESSAGE_TAG_ATTR1 = new State();
    private static final State STATE_UI_MESSAGE_TAG_ATTR2 = new State();
    private static final State STATE_UI_MESSAGE_TEXT = new State();
    private static final State STATE_END_UI_MESSAGE1 = new State();
    private static final State STATE_END_UI_MESSAGE2 = new State();
    private static final State STATE_END_UI_MESSAGE3 = new State();
    private static final State STATE_END_UI_MESSAGE4 = new State();
    private static final State STATE_END_UI_MESSAGE5 = new State();
    private static final State STATE_END_UI_MESSAGE6 = new State();
    private static final State STATE_END_UI_MESSAGE7 = new State();
    private static final State STATE_END_UI_MESSAGE8 = new State();
    private static final State STATE_END_UI_MESSAGE9 = new State();
    private static final State STATE_END_UI_MESSAGE10 = new State();
    private static final State STATE_END_UI_MESSAGE11 = new State();
    private static final State STATE_END_UI_MESSAGE12 = new State();

    private static final State STATE_START_UI_ERROR1 = new State();
    private static final State STATE_START_UI_ERROR2 = new State();
    private static final State STATE_START_UI_ERROR3 = new State();
    private static final State STATE_START_UI_ERROR4 = new State();
    private static final State STATE_START_UI_ERROR5 = new State();
    private static final State STATE_IN_UI_ERROR_TAG = new State();
    private static final State STATE_UI_ERROR_TAG_ATTR1 = new State();
    private static final State STATE_UI_ERROR_TAG_ATTR2 = new State();
    private static final State STATE_UI_ERROR_TEXT = new State();
    private static final State STATE_END_UI_ERROR1 = new State();
    private static final State STATE_END_UI_ERROR2 = new State();
    private static final State STATE_END_UI_ERROR3 = new State();
    private static final State STATE_END_UI_ERROR4 = new State();
    private static final State STATE_END_UI_ERROR5 = new State();
    private static final State STATE_END_UI_ERROR6 = new State();
    private static final State STATE_END_UI_ERROR7 = new State();
    private static final State STATE_END_UI_ERROR8 = new State();
    private static final State STATE_END_UI_ERROR9 = new State();
    private static final State STATE_END_UI_ERROR10 = new State();

    /** The initial state for the state machine. */
    private static final State INITIAL_STATE = STATE_TRIM_WHITESPACE;
    
    private static final String DEFAULT_MATCH = null;
    private static final String WHITESPACE_MATCH = " \t\n\r";
    
    static
    {
        //Set up state transitions
        final Boolean TRUE = Boolean.TRUE;
        final Boolean FALSE = Boolean.FALSE;
        
        //Data for the state machine is in the form:
        // current state id, characters to match on input, new state id, suppress character flag, text to output
        Object[][] stateData = 
        {
            {STATE_TRIM_WHITESPACE,     WHITESPACE_MATCH,   STATE_TRIM_WHITESPACE,      TRUE,   null},
            {STATE_TRIM_WHITESPACE,     "<",                STATE_START_TAG,            FALSE,  null},
            {STATE_TRIM_WHITESPACE,     DEFAULT_MATCH,      STATE_IN_TEXT,              FALSE,  null},

            {STATE_IN_TEXT,             WHITESPACE_MATCH,   STATE_TRIM_WHITESPACE,      TRUE,   " "},
            {STATE_IN_TEXT,             DEFAULT_MATCH,      STATE_IN_TEXT,              FALSE,  null},
            {STATE_IN_TEXT,             "<",                STATE_START_TAG,            FALSE,  null},

            {STATE_START_TAG,           "!",                STATE_START_COMMENT1,       FALSE,  null},
            {STATE_START_TAG,           "Pp",               STATE_START_PRE1,           FALSE,  null},
            {STATE_START_TAG,           "Ss",               STATE_START_SCRIPT1,        FALSE,  null},
            {STATE_START_TAG,           "Tt",               STATE_START_TEXTAREA1,      FALSE,  null},
            {STATE_START_TAG,           "u",                STATE_START_UI1,            FALSE,  null},
            {STATE_START_TAG,           DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TAG,           WHITESPACE_MATCH,   STATE_START_TAG,            TRUE,   null},

            {STATE_START_COMMENT1,      "-",                STATE_START_COMMENT2,       FALSE,  null},
            {STATE_START_COMMENT1,      "[",                STATE_START_CDATA1,         FALSE,  null},
            {STATE_START_COMMENT1,      DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_COMMENT1,      ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_COMMENT2,      "-",                STATE_IN_COMMENT,           FALSE,  null},
            {STATE_START_COMMENT2,      DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_COMMENT2,      ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_IN_COMMENT,          "-",                STATE_END_COMMENT1,         TRUE,   null},
            {STATE_IN_COMMENT,          DEFAULT_MATCH,      STATE_IN_COMMENT,           TRUE,   null},
            {STATE_END_COMMENT1,        "-",                STATE_END_COMMENT2,         TRUE,   null},
            {STATE_END_COMMENT1,        DEFAULT_MATCH,      STATE_IN_COMMENT,           TRUE,   null},
            {STATE_END_COMMENT2,        ">",                STATE_IN_TEXT,              TRUE,   " -->"},
            {STATE_END_COMMENT2,        DEFAULT_MATCH,      STATE_IN_COMMENT,           TRUE,   null},

            {STATE_START_CDATA1,        "C",                STATE_START_CDATA2,         FALSE,  null},
            {STATE_START_CDATA1,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA1,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_CDATA2,        "D",                STATE_START_CDATA3,         FALSE,  null},
            {STATE_START_CDATA2,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA2,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_CDATA3,        "A",                STATE_START_CDATA4,         FALSE,  null},
            {STATE_START_CDATA3,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA3,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_CDATA4,        "T",                STATE_START_CDATA5,         FALSE,  null},
            {STATE_START_CDATA4,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA4,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_CDATA5,        "A",                STATE_START_CDATA6,         FALSE,  null},
            {STATE_START_CDATA5,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA5,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_CDATA6,        "[",                STATE_CDATA_TEXT,           FALSE,  null},
            {STATE_START_CDATA6,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_CDATA6,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_CDATA_TEXT,          "]",                STATE_END_CDATA1,           FALSE,  null},
            {STATE_CDATA_TEXT,          DEFAULT_MATCH,      STATE_CDATA_TEXT,           FALSE,  null},
            {STATE_END_CDATA1,          "]",                STATE_END_CDATA2,           FALSE,  null},
            {STATE_END_CDATA1,          DEFAULT_MATCH,      STATE_CDATA_TEXT,           FALSE,  null},
            {STATE_END_CDATA2,          ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_END_CDATA2,          DEFAULT_MATCH,      STATE_CDATA_TEXT,           FALSE,  null},

            {STATE_START_PRE1,          "Rr",               STATE_START_PRE2,           FALSE,  null},
            {STATE_START_PRE1,          DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_PRE1,          ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_PRE2,          "Ee",               STATE_START_PRE3,           FALSE,  null},
            {STATE_START_PRE2,          DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_PRE2,          ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_PRE3,          ">",                STATE_PRE_TEXT,             FALSE,  null},
            {STATE_START_PRE3,          WHITESPACE_MATCH,   STATE_IN_PRE_TAG,           FALSE,  null},
            {STATE_START_PRE3,          DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_IN_PRE_TAG,          "'",                STATE_PRE_TAG_ATTR1,        FALSE,  null},
            {STATE_IN_PRE_TAG,          "\"",               STATE_PRE_TAG_ATTR2,        FALSE,  null},
            {STATE_IN_PRE_TAG,          ">",                STATE_PRE_TEXT,             FALSE,  null},
            {STATE_IN_PRE_TAG,          DEFAULT_MATCH,      STATE_IN_PRE_TAG,           FALSE,  null},
            {STATE_PRE_TAG_ATTR1,       "'",                STATE_IN_PRE_TAG,           FALSE,  null},
            {STATE_PRE_TAG_ATTR1,       DEFAULT_MATCH,      STATE_PRE_TAG_ATTR1,        FALSE,  null},
            {STATE_PRE_TAG_ATTR2,       "\"",               STATE_IN_PRE_TAG,           FALSE,  null},
            {STATE_PRE_TAG_ATTR2,       DEFAULT_MATCH,      STATE_PRE_TAG_ATTR2,        FALSE,  null},
            {STATE_PRE_TEXT,            "<",                STATE_END_PRE1,             FALSE,  null},
            {STATE_PRE_TEXT,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},
            {STATE_END_PRE1,            "/",                STATE_END_PRE2,             FALSE,  null},
            {STATE_END_PRE1,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},
            {STATE_END_PRE2,            "Pp",               STATE_END_PRE3,             FALSE,  null},
            {STATE_END_PRE2,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},
            {STATE_END_PRE3,            "Rr",               STATE_END_PRE4,             FALSE,  null},
            {STATE_END_PRE3,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},
            {STATE_END_PRE4,            "Ee",               STATE_END_PRE5,             FALSE,  null},
            {STATE_END_PRE4,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},
            {STATE_END_PRE5,            ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_END_PRE5,            WHITESPACE_MATCH,   STATE_END_PRE5,             FALSE,  null},
            {STATE_END_PRE5,            DEFAULT_MATCH,      STATE_PRE_TEXT,             FALSE,  null},

            {STATE_START_SCRIPT1,       "Cc",               STATE_START_SCRIPT2,        FALSE,  null},
            {STATE_START_SCRIPT1,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_SCRIPT1,       ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_SCRIPT2,       "Rr",               STATE_START_SCRIPT3,        FALSE,  null},
            {STATE_START_SCRIPT2,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_SCRIPT2,       ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_SCRIPT3,       "Ii",               STATE_START_SCRIPT4,        FALSE,  null},
            {STATE_START_SCRIPT3,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_SCRIPT3,       ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_SCRIPT4,       "Pp",               STATE_START_SCRIPT5,        FALSE,  null},
            {STATE_START_SCRIPT4,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_SCRIPT4,       ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_SCRIPT5,       "Tt",               STATE_START_SCRIPT6,        FALSE,  null},
            {STATE_START_SCRIPT5,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_SCRIPT5,       ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_SCRIPT6,       ">",                STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_START_SCRIPT6,       WHITESPACE_MATCH,   STATE_IN_SCRIPT_TAG,        FALSE,  null},
            {STATE_START_SCRIPT6,       DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_IN_SCRIPT_TAG,       "'",                STATE_SCRIPT_ATTR1,         FALSE,  null},
            {STATE_IN_SCRIPT_TAG,       "\"",               STATE_SCRIPT_ATTR2,         FALSE,  null},
            {STATE_IN_SCRIPT_TAG,       ">",                STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_IN_SCRIPT_TAG,       DEFAULT_MATCH,      STATE_IN_SCRIPT_TAG,        FALSE,  null},
            {STATE_SCRIPT_ATTR1,        "'",                STATE_IN_SCRIPT_TAG,        FALSE,  null},
            {STATE_SCRIPT_ATTR1,        DEFAULT_MATCH,      STATE_SCRIPT_ATTR1,         FALSE,  null},
            {STATE_SCRIPT_ATTR2,        "\"",               STATE_IN_SCRIPT_TAG,        FALSE,  null},
            {STATE_SCRIPT_ATTR2,        DEFAULT_MATCH,      STATE_SCRIPT_ATTR2,         FALSE,  null},
            {STATE_SCRIPT_TEXT,         "<",                STATE_END_SCRIPT1,          FALSE,  null},
            {STATE_SCRIPT_TEXT,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT1,         "/",                STATE_END_SCRIPT2,          FALSE,  null},
            {STATE_END_SCRIPT1,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT2,         "Ss",               STATE_END_SCRIPT3,          FALSE,  null},
            {STATE_END_SCRIPT2,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT3,         "Cc",               STATE_END_SCRIPT4,          FALSE,  null},
            {STATE_END_SCRIPT3,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT4,         "Rr",               STATE_END_SCRIPT5,          FALSE,  null},
            {STATE_END_SCRIPT4,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT5,         "Ii",               STATE_END_SCRIPT6,          FALSE,  null},
            {STATE_END_SCRIPT5,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT6,         "Pp",               STATE_END_SCRIPT7,          FALSE,  null},
            {STATE_END_SCRIPT6,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT7,         "Tt",               STATE_END_SCRIPT8,          FALSE,  null},
            {STATE_END_SCRIPT7,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},
            {STATE_END_SCRIPT8,         ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_END_SCRIPT8,         WHITESPACE_MATCH,   STATE_END_SCRIPT8,          FALSE,  null},
            {STATE_END_SCRIPT8,         DEFAULT_MATCH,      STATE_SCRIPT_TEXT,          FALSE,  null},

            {STATE_START_TEXTAREA1,     "Ee",               STATE_START_TEXTAREA2,      FALSE,  null},
            {STATE_START_TEXTAREA1,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA1,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA2,     "Xx",               STATE_START_TEXTAREA3,      FALSE,  null},
            {STATE_START_TEXTAREA2,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA2,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA3,     "Tt",               STATE_START_TEXTAREA4,      FALSE,  null},
            {STATE_START_TEXTAREA3,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA3,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA4,     "Aa",               STATE_START_TEXTAREA5,      FALSE,  null},
            {STATE_START_TEXTAREA4,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA4,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA5,     "Rr",               STATE_START_TEXTAREA6,      FALSE,  null},
            {STATE_START_TEXTAREA5,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA5,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA6,     "Ee",               STATE_START_TEXTAREA7,      FALSE,  null},
            {STATE_START_TEXTAREA6,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA6,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA7,     "Aa",               STATE_START_TEXTAREA8,      FALSE,  null},
            {STATE_START_TEXTAREA7,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_TEXTAREA7,     ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_TEXTAREA8,     ">",                STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_START_TEXTAREA8,     WHITESPACE_MATCH,   STATE_IN_TEXTAREA_TAG,      FALSE,  null},
            {STATE_START_TEXTAREA8,     DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_IN_TEXTAREA_TAG,     "'",                STATE_TEXTAREA_TAG_ATTR1,   FALSE,  null},
            {STATE_IN_TEXTAREA_TAG,     "\"",               STATE_TEXTAREA_TAG_ATTR2,   FALSE,  null},
            {STATE_IN_TEXTAREA_TAG,     ">",                STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_IN_TEXTAREA_TAG,     DEFAULT_MATCH,      STATE_IN_TEXTAREA_TAG,      FALSE,  null},
            {STATE_TEXTAREA_TAG_ATTR1,  "'",                STATE_IN_TEXTAREA_TAG,      FALSE,  null},
            {STATE_TEXTAREA_TAG_ATTR1,  DEFAULT_MATCH,      STATE_TEXTAREA_TAG_ATTR1,   FALSE,  null},
            {STATE_TEXTAREA_TAG_ATTR2,  "\"",               STATE_IN_TEXTAREA_TAG,      FALSE,  null},
            {STATE_TEXTAREA_TAG_ATTR2,  DEFAULT_MATCH,      STATE_TEXTAREA_TAG_ATTR2,   FALSE,  null},
            {STATE_TEXTAREA_TEXT,       "<",                STATE_END_TEXTAREA1,        FALSE,  null},
            {STATE_TEXTAREA_TEXT,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA1,       "/",                STATE_END_TEXTAREA2,        FALSE,  null},
            {STATE_END_TEXTAREA1,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA2,       "Tt",               STATE_END_TEXTAREA3,        FALSE,  null},
            {STATE_END_TEXTAREA2,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA3,       "Ee",               STATE_END_TEXTAREA4,        FALSE,  null},
            {STATE_END_TEXTAREA3,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA4,       "Xx",               STATE_END_TEXTAREA5,        FALSE,  null},
            {STATE_END_TEXTAREA4,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA5,       "Tt",               STATE_END_TEXTAREA6,        FALSE,  null},
            {STATE_END_TEXTAREA5,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA6,       "Aa",               STATE_END_TEXTAREA7,        FALSE,  null},
            {STATE_END_TEXTAREA6,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA7,       "Rr",               STATE_END_TEXTAREA8,        FALSE,  null},
            {STATE_END_TEXTAREA7,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA8,       "Ee",               STATE_END_TEXTAREA9,        FALSE,  null},
            {STATE_END_TEXTAREA8,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA9,       "Aa",               STATE_END_TEXTAREA10,       FALSE,  null},
            {STATE_END_TEXTAREA9,       DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},
            {STATE_END_TEXTAREA10,      ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_END_TEXTAREA10,      WHITESPACE_MATCH,   STATE_END_TEXTAREA10,       FALSE,  null},
            {STATE_END_TEXTAREA10,      DEFAULT_MATCH,      STATE_TEXTAREA_TEXT,        FALSE,  null},

            {STATE_START_UI1,           "i",                STATE_START_UI2,            FALSE,  null},
            {STATE_START_UI1,           DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI1,           ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_UI2,           ":",                STATE_START_UI3,            FALSE,  null},
            {STATE_START_UI2,           DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI2,           ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_UI3,           "t",                STATE_START_UI_TEXT1,       FALSE,  null},
            {STATE_START_UI3,           "m",                STATE_START_UI_MESSAGE1,    FALSE,  null},
            {STATE_START_UI3,           "e",                STATE_START_UI_ERROR1,      FALSE,  null},
            {STATE_START_UI3,           DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI3,           ">",                STATE_IN_TEXT,              FALSE,  null},
            
            {STATE_START_UI_TEXT1,     "e",                 STATE_START_UI_TEXT2,       FALSE,  null},
            {STATE_START_UI_TEXT1,     DEFAULT_MATCH,       STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI_TEXT1,     ">",                 STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_UI_TEXT2,     "x",                 STATE_START_UI_TEXT3,       FALSE,  null},
            {STATE_START_UI_TEXT2,     DEFAULT_MATCH,       STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI_TEXT2,     ">",                 STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_UI_TEXT3,     "t",                 STATE_START_UI_TEXT4,       FALSE,  null},
            {STATE_START_UI_TEXT3,     DEFAULT_MATCH,       STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI_TEXT3,     ">",                 STATE_IN_TEXT,              FALSE,  null},
            {STATE_START_UI_TEXT4,     "A",                 STATE_START_UI_TEXTAREA5,   FALSE,  null},
            {STATE_START_UI_TEXT4,     WHITESPACE_MATCH,    STATE_IN_UI_TEXT_TAG,       FALSE,  null},
            {STATE_START_UI_TEXT4,     DEFAULT_MATCH,       STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_START_UI_TEXT4,     ">",                 STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_IN_UI_TEXT_TAG,     "'",                 STATE_UI_TEXT_TAG_ATTR1,    FALSE,  null},
            {STATE_IN_UI_TEXT_TAG,     "\"",                STATE_UI_TEXT_TAG_ATTR2,    FALSE,  null},
            {STATE_IN_UI_TEXT_TAG,     ">",                 STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_IN_UI_TEXT_TAG,     DEFAULT_MATCH,       STATE_IN_UI_TEXT_TAG,       FALSE,  null},
            {STATE_UI_TEXT_TAG_ATTR1,  "'",                 STATE_IN_UI_TEXT_TAG,       FALSE,  null},
            {STATE_UI_TEXT_TAG_ATTR1,  DEFAULT_MATCH,       STATE_UI_TEXT_TAG_ATTR1,    FALSE,  null},
            {STATE_UI_TEXT_TAG_ATTR2,  "\"",                STATE_IN_UI_TEXT_TAG,       FALSE,  null},
            {STATE_UI_TEXT_TAG_ATTR2,  DEFAULT_MATCH,       STATE_UI_TEXT_TAG_ATTR2,    FALSE,  null},
            {STATE_UI_TEXT_TEXT,       "<",                 STATE_END_UI_TEXT1,         FALSE,  null},
            {STATE_UI_TEXT_TEXT,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT1,       "/",                 STATE_END_UI_TEXT2,         FALSE,  null},
            {STATE_END_UI_TEXT1,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT2,       "u",                 STATE_END_UI_TEXT3,         FALSE,  null},
            {STATE_END_UI_TEXT2,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT3,       "i",                 STATE_END_UI_TEXT4,         FALSE,  null},
            {STATE_END_UI_TEXT3,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT4,       ":",                 STATE_END_UI_TEXT5,         FALSE,  null},
            {STATE_END_UI_TEXT4,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT5,       "t",                 STATE_END_UI_TEXT6,         FALSE,  null},
            {STATE_END_UI_TEXT5,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT6,       "e",                 STATE_END_UI_TEXT7,         FALSE,  null},
            {STATE_END_UI_TEXT6,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT7,       "x",                 STATE_END_UI_TEXT8,         FALSE,  null},
            {STATE_END_UI_TEXT7,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            {STATE_END_UI_TEXT8,       "t",                 STATE_END_UI_TEXT9,         FALSE,  null},
            {STATE_END_UI_TEXT8,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},            
            {STATE_END_UI_TEXT9,       ">",                 STATE_IN_TEXT,              FALSE,  null},
            {STATE_END_UI_TEXT9,       WHITESPACE_MATCH,    STATE_END_UI_TEXT9,         FALSE,  null},
            {STATE_END_UI_TEXT9,       DEFAULT_MATCH,       STATE_UI_TEXT_TEXT,         FALSE,  null},
            
            {STATE_START_UI_TEXTAREA5,     "r",              STATE_START_UI_TEXTAREA6,    FALSE,  null},
            {STATE_START_UI_TEXTAREA5,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_TEXTAREA5,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_TEXTAREA6,     "e",              STATE_START_UI_TEXTAREA7,    FALSE,  null},
            {STATE_START_UI_TEXTAREA6,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_TEXTAREA6,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_TEXTAREA7,     "a",              STATE_START_UI_TEXTAREA8,    FALSE,  null},
            {STATE_START_UI_TEXTAREA7,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_TEXTAREA7,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_TEXTAREA8,     ">",              STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_START_UI_TEXTAREA8,     WHITESPACE_MATCH, STATE_IN_UI_TEXTAREA_TAG,    FALSE,  null},
            {STATE_START_UI_TEXTAREA8,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_IN_UI_TEXTAREA_TAG,     "'",              STATE_UI_TEXTAREA_TAG_ATTR1, FALSE,  null},
            {STATE_IN_UI_TEXTAREA_TAG,     "\"",             STATE_UI_TEXTAREA_TAG_ATTR2, FALSE,  null},
            {STATE_IN_UI_TEXTAREA_TAG,     ">",              STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_IN_UI_TEXTAREA_TAG,     DEFAULT_MATCH,    STATE_IN_UI_TEXTAREA_TAG,    FALSE,  null},
            {STATE_UI_TEXTAREA_TAG_ATTR1,  "'",              STATE_IN_UI_TEXTAREA_TAG,    FALSE,  null},
            {STATE_UI_TEXTAREA_TAG_ATTR1,  DEFAULT_MATCH,    STATE_UI_TEXTAREA_TAG_ATTR1, FALSE,  null},
            {STATE_UI_TEXTAREA_TAG_ATTR2,  "\"",             STATE_IN_UI_TEXTAREA_TAG,    FALSE,  null},
            {STATE_UI_TEXTAREA_TAG_ATTR2,  DEFAULT_MATCH,    STATE_UI_TEXTAREA_TAG_ATTR2, FALSE,  null},
            {STATE_UI_TEXTAREA_TEXT,       "<",              STATE_END_UI_TEXTAREA1,      FALSE,  null},
            {STATE_UI_TEXTAREA_TEXT,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA1,       "/",              STATE_END_UI_TEXTAREA2,      FALSE,  null},
            {STATE_END_UI_TEXTAREA1,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA2,       "u",              STATE_END_UI_TEXTAREA3,      FALSE,  null},
            {STATE_END_UI_TEXTAREA2,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA3,       "i",              STATE_END_UI_TEXTAREA4,      FALSE,  null},
            {STATE_END_UI_TEXTAREA3,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA4,       ":",              STATE_END_UI_TEXTAREA5,      FALSE,  null},
            {STATE_END_UI_TEXTAREA4,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA5,       "t",              STATE_END_UI_TEXTAREA6,      FALSE,  null},
            {STATE_END_UI_TEXTAREA5,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA6,       "e",              STATE_END_UI_TEXTAREA7,      FALSE,  null},
            {STATE_END_UI_TEXTAREA6,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA7,       "x",              STATE_END_UI_TEXTAREA8,      FALSE,  null},
            {STATE_END_UI_TEXTAREA7,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA8,       "t",              STATE_END_UI_TEXTAREA9,      FALSE,  null},
            {STATE_END_UI_TEXTAREA8,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA9,       "A",              STATE_END_UI_TEXTAREA10,     FALSE,  null},
            {STATE_END_UI_TEXTAREA9,       DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA10,      "r",              STATE_END_UI_TEXTAREA11,     FALSE,  null},
            {STATE_END_UI_TEXTAREA10,      DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA11,      "e",              STATE_END_UI_TEXTAREA12,     FALSE,  null},
            {STATE_END_UI_TEXTAREA11,      DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            {STATE_END_UI_TEXTAREA12,      "a",              STATE_END_UI_TEXTAREA13,     FALSE,  null},
            {STATE_END_UI_TEXTAREA12,      DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},            
            {STATE_END_UI_TEXTAREA13,      ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_END_UI_TEXTAREA13,      WHITESPACE_MATCH, STATE_END_UI_TEXTAREA13,     FALSE,  null},
            {STATE_END_UI_TEXTAREA13,      DEFAULT_MATCH,    STATE_UI_TEXTAREA_TEXT,      FALSE,  null},
            
            {STATE_START_UI_MESSAGE1,     "e",              STATE_START_UI_MESSAGE2,     FALSE,  null},
            {STATE_START_UI_MESSAGE1,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE1,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE2,     "s",              STATE_START_UI_MESSAGE3,     FALSE,  null},
            {STATE_START_UI_MESSAGE2,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE2,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE3,     "s",              STATE_START_UI_MESSAGE4,     FALSE,  null},
            {STATE_START_UI_MESSAGE3,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE3,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE4,     "a",              STATE_START_UI_MESSAGE5,     FALSE,  null},
            {STATE_START_UI_MESSAGE4,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE4,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE5,     "g",              STATE_START_UI_MESSAGE6,     FALSE,  null},
            {STATE_START_UI_MESSAGE5,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE5,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE6,     "e",              STATE_START_UI_MESSAGE7,     FALSE,  null},
            {STATE_START_UI_MESSAGE6,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_MESSAGE6,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_MESSAGE7,     ">",              STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_START_UI_MESSAGE7,     WHITESPACE_MATCH, STATE_IN_UI_MESSAGE_TAG,     FALSE,  null},
            {STATE_START_UI_MESSAGE7,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_IN_UI_MESSAGE_TAG,     "'",              STATE_UI_MESSAGE_TAG_ATTR1,  FALSE,  null},
            {STATE_IN_UI_MESSAGE_TAG,     "\"",             STATE_UI_MESSAGE_TAG_ATTR2,  FALSE,  null},
            {STATE_IN_UI_MESSAGE_TAG,     ">",              STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_IN_UI_MESSAGE_TAG,     DEFAULT_MATCH,    STATE_IN_UI_MESSAGE_TAG,     FALSE,  null},
            {STATE_UI_MESSAGE_TAG_ATTR1,  "'",              STATE_IN_UI_MESSAGE_TAG,     FALSE,  null},
            {STATE_UI_MESSAGE_TAG_ATTR1,  DEFAULT_MATCH,    STATE_UI_MESSAGE_TAG_ATTR1,  FALSE,  null},
            {STATE_UI_MESSAGE_TAG_ATTR2,  "\"",             STATE_IN_UI_MESSAGE_TAG,     FALSE,  null},
            {STATE_UI_MESSAGE_TAG_ATTR2,  DEFAULT_MATCH,    STATE_UI_MESSAGE_TAG_ATTR2,  FALSE,  null},
            {STATE_UI_MESSAGE_TEXT,       "<",              STATE_END_UI_MESSAGE1,       FALSE,  null},
            {STATE_UI_MESSAGE_TEXT,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE1,       "/",              STATE_END_UI_MESSAGE2,       FALSE,  null},
            {STATE_END_UI_MESSAGE1,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE2,       "u",              STATE_END_UI_MESSAGE3,       FALSE,  null},
            {STATE_END_UI_MESSAGE2,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE3,       "i",              STATE_END_UI_MESSAGE4,       FALSE,  null},
            {STATE_END_UI_MESSAGE3,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE4,       ":",              STATE_END_UI_MESSAGE5,       FALSE,  null},
            {STATE_END_UI_MESSAGE4,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE5,       "m",              STATE_END_UI_MESSAGE6,       FALSE,  null},
            {STATE_END_UI_MESSAGE5,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE6,       "e",              STATE_END_UI_MESSAGE7,       FALSE,  null},
            {STATE_END_UI_MESSAGE6,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE7,       "s",              STATE_END_UI_MESSAGE8,       FALSE,  null},
            {STATE_END_UI_MESSAGE7,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE8,       "s",              STATE_END_UI_MESSAGE9,       FALSE,  null},
            {STATE_END_UI_MESSAGE8,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE9,       "a",              STATE_END_UI_MESSAGE10,      FALSE,  null},
            {STATE_END_UI_MESSAGE9,       DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE10,      "g",              STATE_END_UI_MESSAGE11,      FALSE,  null},
            {STATE_END_UI_MESSAGE10,      DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},
            {STATE_END_UI_MESSAGE11,      "e",              STATE_END_UI_MESSAGE12,      FALSE,  null},
            {STATE_END_UI_MESSAGE11,      DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},            
            {STATE_END_UI_MESSAGE12,      ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_END_UI_MESSAGE12,      WHITESPACE_MATCH, STATE_END_UI_MESSAGE12,      FALSE,  null},
            {STATE_END_UI_MESSAGE12,      DEFAULT_MATCH,    STATE_UI_MESSAGE_TEXT,       FALSE,  null},

            {STATE_START_UI_ERROR1,     "r",              STATE_START_UI_ERROR2,       FALSE,  null},
            {STATE_START_UI_ERROR1,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_ERROR1,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_ERROR2,     "r",              STATE_START_UI_ERROR3,       FALSE,  null},
            {STATE_START_UI_ERROR2,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_ERROR2,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_ERROR3,     "o",              STATE_START_UI_ERROR4,       FALSE,  null},
            {STATE_START_UI_ERROR3,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_ERROR3,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_ERROR4,     "r",              STATE_START_UI_ERROR5,       FALSE,  null},
            {STATE_START_UI_ERROR4,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_START_UI_ERROR4,     ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_START_UI_ERROR5,     ">",              STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_START_UI_ERROR5,     WHITESPACE_MATCH, STATE_IN_UI_ERROR_TAG,       FALSE,  null},
            {STATE_START_UI_ERROR5,     DEFAULT_MATCH,    STATE_IN_OTHER_TAG,          FALSE,  null},
            {STATE_IN_UI_ERROR_TAG,     "'",              STATE_UI_ERROR_TAG_ATTR1,    FALSE,  null},
            {STATE_IN_UI_ERROR_TAG,     "\"",             STATE_UI_ERROR_TAG_ATTR2,    FALSE,  null},
            {STATE_IN_UI_ERROR_TAG,     ">",              STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_IN_UI_ERROR_TAG,     DEFAULT_MATCH,    STATE_IN_UI_ERROR_TAG,       FALSE,  null},
            {STATE_UI_ERROR_TAG_ATTR1,  "'",              STATE_IN_UI_ERROR_TAG,       FALSE,  null},
            {STATE_UI_ERROR_TAG_ATTR1,  DEFAULT_MATCH,    STATE_UI_ERROR_TAG_ATTR1,    FALSE,  null},
            {STATE_UI_ERROR_TAG_ATTR2,  "\"",             STATE_IN_UI_ERROR_TAG,       FALSE,  null},
            {STATE_UI_ERROR_TAG_ATTR2,  DEFAULT_MATCH,    STATE_UI_ERROR_TAG_ATTR2,    FALSE,  null},
            {STATE_UI_ERROR_TEXT,       "<",              STATE_END_UI_ERROR1,         FALSE,  null},
            {STATE_UI_ERROR_TEXT,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR1,       "/",              STATE_END_UI_ERROR2,         FALSE,  null},
            {STATE_END_UI_ERROR1,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR2,       "u",              STATE_END_UI_ERROR3,         FALSE,  null},
            {STATE_END_UI_ERROR2,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR3,       "i",              STATE_END_UI_ERROR4,         FALSE,  null},
            {STATE_END_UI_ERROR3,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR4,       ":",              STATE_END_UI_ERROR5,         FALSE,  null},
            {STATE_END_UI_ERROR4,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR5,       "e",              STATE_END_UI_ERROR6,         FALSE,  null},
            {STATE_END_UI_ERROR5,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR6,       "r",              STATE_END_UI_ERROR7,         FALSE,  null},
            {STATE_END_UI_ERROR6,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR7,       "r",              STATE_END_UI_ERROR8,         FALSE,  null},
            {STATE_END_UI_ERROR7,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR8,       "o",              STATE_END_UI_ERROR9,         FALSE,  null},
            {STATE_END_UI_ERROR8,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR9,       "r",              STATE_END_UI_ERROR10,        FALSE,  null},
            {STATE_END_UI_ERROR9,       DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            {STATE_END_UI_ERROR10,      ">",              STATE_IN_TEXT,               FALSE,  null},
            {STATE_END_UI_ERROR10,      WHITESPACE_MATCH, STATE_END_UI_ERROR10,        FALSE,  null},
            {STATE_END_UI_ERROR10,      DEFAULT_MATCH,    STATE_UI_ERROR_TEXT,         FALSE,  null},
            
            {STATE_IN_OTHER_TAG,        "'",                STATE_OTHER_TAG_ATTR1,      FALSE,  null},
            {STATE_IN_OTHER_TAG,        "\"",               STATE_OTHER_TAG_ATTR2,      FALSE,  null},
            {STATE_IN_OTHER_TAG,        ">",                STATE_IN_TEXT,              FALSE,  null},
            {STATE_IN_OTHER_TAG,        DEFAULT_MATCH,      STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_OTHER_TAG_ATTR1,     "'",                STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_OTHER_TAG_ATTR1,     DEFAULT_MATCH,      STATE_OTHER_TAG_ATTR1,      FALSE,  null},
            {STATE_OTHER_TAG_ATTR2,     "\"",               STATE_IN_OTHER_TAG,         FALSE,  null},
            {STATE_OTHER_TAG_ATTR2,     DEFAULT_MATCH,      STATE_OTHER_TAG_ATTR2,      FALSE,  null}
        };
        
        for (int i = 0; i < stateData.length; i++)
        {
            State state = (State) stateData[i][0];
            String chars = (String) stateData[i][1];
            State targetState = (State) stateData[i][2];
            boolean suppressCurrentChar = ((Boolean) stateData[i][3]).booleanValue();
            byte[] outputBytes = null;
            
            try
            {
                if (stateData[i][4] != null)
                {
                    outputBytes = ((String) stateData[i][4]).getBytes("UTF-8");
                }
            }
            catch (UnsupportedEncodingException e)
            {
                //Ok, this shouldn't ever happen, but we want to do something sensible. Set up a single-state that does nothing
                Log logger = LogFactory.getLog(WhiteSpaceFilterStateMachine.class);
                logger.error("UTF-8 encoding unsupported, white space filtering will be disabled", e);
                
                INITIAL_STATE.inputChars = new char[0];
                INITIAL_STATE.stateChanges = new StateChange[0];
                INITIAL_STATE.defaultStateChange = new StateChange(INITIAL_STATE, false, null);
                break;
            }
            
            StateChange stateChange = new StateChange(targetState, suppressCurrentChar, outputBytes);
            
            if (chars == null)
            {
                state.setDefaultStateChange(stateChange);
            }
            else
            {
                for (int j = 0; j < chars.length(); j++) 
                {
                    state.addChange(chars.charAt(j), stateChange);
                }
            }
        }
    }
    
    /** The current state of the state machine. */
    private State currentState;
    
    /** 
     * Creates a new WhiteSpaceFilterOutputStream.
     */ 
    public WhiteSpaceFilterStateMachine()
    {
        this.currentState = INITIAL_STATE;
    }
    
    /**
     * Moves the machine to the next state, based on the input character.
     * 
     * @param c the input character.
     * @return the state change.
     */
    public StateChange nextState(final char c)
    {
        StateChange change = currentState.getChange(c);
        currentState = change.newState;
        
        return change;
    }
    
    /**
     * Represents a state in the state-machine.
     */
    private static final class State
    {
        /** State changes indexed by character. */
        private char[] inputChars;
        
        /** States transitions for each character. */
        private StateChange[] stateChanges;
        
        /** The default state transition (when no matching character is found). */
        private StateChange defaultStateChange;        
        
        /** Creates a state. */
        public State()
        {
            inputChars = new char[0];
            stateChanges = new StateChange[0];
        }
        
        /**
         * Adds a possible state transition.
         * 
         * @param c the character which triggers the change.
         * @param change the state to transition to when the character is encountered.
         */
        public void addChange(final char c, final StateChange change)
        {
            //Yes, this is inefficient, but it's only going to be done during the static initializer
            char[] newInputChars = new char[inputChars.length + 1];
            StateChange[] newStateChanges = new StateChange[stateChanges.length + 1];
            
            System.arraycopy(inputChars, 0, newInputChars, 0, inputChars.length);
            System.arraycopy(stateChanges, 0, newStateChanges, 0, stateChanges.length);            
            newInputChars[inputChars.length] = c;
            newStateChanges[stateChanges.length] = change;
            
            inputChars = newInputChars;
            stateChanges = newStateChanges;
        }
        
        /**
         * Sets the default state change, used when no matching character is found.
         * @param change the default state change.
         */
        public void setDefaultStateChange(final StateChange change)
        {
            this.defaultStateChange = change;
        }
        
        /** 
         * Gets the state change for the given character.
         * 
         * @param c the input character.
         * @return the state to transition to.
         */        
        public StateChange getChange(final char c)
        {
            for (int i = 0; i < inputChars.length; i++)
            {
                if (c == inputChars[i])
                {
                    return stateChanges[i];                    
                }
            }
            
            return defaultStateChange;
        }
    }
    
    /** Controls what processing occurs on a state transition. */
    
    protected static final class StateChange
    {
        /** The state to transition to. */
        public final State newState;
        
        /** Indicates character which triggered the change should be suppressed. */
        public final boolean suppressCurrentChar;
        
        /** Data to output on transition to the new state, if applicable. */
        public final byte[] outputBytes;
        
        /**
         * Creates a StateChange.
         * 
         * @param newState the new State.
         * @param suppressCurrentChar whether the character which triggered the change should be suppressed.
         * @param outputBytes the data to output on transition to the new state, or null for no output.
         */
        public StateChange(final State newState, final boolean suppressCurrentChar, final byte[] outputBytes)
        {
            this.newState = newState;
            this.suppressCurrentChar = suppressCurrentChar;
            this.outputBytes = outputBytes;
        }
    }
}
