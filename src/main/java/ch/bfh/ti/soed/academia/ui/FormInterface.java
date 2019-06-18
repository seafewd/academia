/*
 * Copyright (c) 2018 Eric Dubuis, Berner Fachhochschule, Switzerland.
 *
 * Project 'academia-ng'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */
package ch.bfh.ti.soed.academia.ui;

/**
 * The Form interface.
 */
public interface FormInterface {
    void save();
    void cancel();
    void delete();
    void formChanged();
}
