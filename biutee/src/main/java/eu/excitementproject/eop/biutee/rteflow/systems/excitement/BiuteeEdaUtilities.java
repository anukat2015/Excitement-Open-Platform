package eu.excitementproject.eop.biutee.rteflow.systems.excitement;

import java.io.File;

import org.apache.uima.jcas.JCas;

import eu.excitementproject.eop.biutee.classifiers.Classifier;
import eu.excitementproject.eop.biutee.classifiers.ClassifierException;
import eu.excitementproject.eop.biutee.classifiers.ClassifierUtils;
import eu.excitementproject.eop.biutee.rteflow.systems.rtepairs.PairData;
import eu.excitementproject.eop.biutee.rteflow.systems.rtepairs.PairProcessor;
import eu.excitementproject.eop.common.DecisionLabel;
import eu.excitementproject.eop.common.TEDecision;
import eu.excitementproject.eop.transformations.utilities.TeEngineMlException;

/**
 * 
 * @author Asher Stern
 * @since Jan 23, 2013
 *
 */
public class BiuteeEdaUtilities
{
	public static void convertExcitementConfigurationFileToBiuConfigurationFile(File excitementConfigurationFile, File biuConfigurationFile)
	{
		// TODO
		throw new RuntimeException("Not yet implemented.");
	}
	
	public static PairData convertJCasToPairData(JCas aCas) throws TeEngineMlException
	{
		// TODO
		throw new RuntimeException("Not yet implemented.");
	}
	
	public static String getPairIdFromJCas(JCas aCas) throws TeEngineMlException
	{
		// TODO
		throw new RuntimeException("Not yet implemented.");
	}
	
	public static TEDecision createDecisionFromPairProcessor(final String pairId, PairProcessor pairProcessor, Classifier classifierForPredictions) throws ClassifierException
	{
		final double classification = classifierForPredictions.classify(pairProcessor.getBestTree().getFeatureVector());
		final boolean entailment = ClassifierUtils.classifierResultToBoolean(classification);
		final DecisionLabel decisionLabel;
		if (entailment)
		{
			decisionLabel = DecisionLabel.Entailment;
		}
		else
		{
			decisionLabel = DecisionLabel.NonEntailment;
		}
		
		return new TEDecision()
		{
			@Override
			public String getPairID()
			{
				return pairId;
			}
			
			@Override
			public DecisionLabel getDecision()
			{
				return decisionLabel;
			}
			
			@Override
			public double getConfidence()
			{
				return classification;
			}
		};
	}
}
